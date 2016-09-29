/**
 * @file ResultActivity.java
 * @date 2016-07-20
 * @version 1.80
 * @author Matteo Franco
 * Si occupa di gestire l'activity dei risultati conseguiti dall'utente
 */

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.Score;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.pathprogress.PathProgressController;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.authentication.LoginActivity;
import beaconstrips.clips.client.viewcontroller.building.BuildingSearchActivity;
import beaconstrips.clips.client.viewcontroller.utility.ResultsAdapter;

public class ResultActivity extends AppCompatActivity {

    private Intent i;
    private String TAG = "ResultActivity";
    private PathResult result;
    private int pathId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if(savedInstanceState == null) {

            i = getIntent();
            pathId = i.getIntExtra("pathId", 0);
            String pathName = i.getStringExtra("pathName");
            String buildingName = i.getStringExtra("buildingName");
            double totalScore = i.getDoubleExtra("totalScore", 0.0);
            GregorianCalendar startGlobalTime = (GregorianCalendar) i.getSerializableExtra("startGlobalTime");
            GregorianCalendar finishGlobalTime = new GregorianCalendar();

            long netTimeSeconds = (TimeUnit.MILLISECONDS.toSeconds(finishGlobalTime.getTimeInMillis() - startGlobalTime.getTimeInMillis())) % 60;
            long netTimeMinutes = netTimeSeconds / 60;

            PathProgressController pathProgress = (PathProgressController) i.getSerializableExtra("pathProgress");

            // array dei risultati delle varie prove del percorso
            ArrayList<ProofResult> results = pathProgress.pathProgress.getProofResults();
            Integer numberOfResults = results.size();

            long netTimeProofsSeconds = 0;

            for (int i = 0; i < results.size(); ++i) {
                netTimeProofsSeconds += TimeUnit.MILLISECONDS.toSeconds(results.get(i).getDuration());
            }

            netTimeProofsSeconds = Math.round((netTimeProofsSeconds % 60));
            long netTimeProofsMinutes = (netTimeProofsSeconds / 60);

            result = new PathResult(pathId, pathName, buildingName, startGlobalTime, finishGlobalTime, (int) totalScore, results);


            ((TextView) findViewById(R.id.totalScore)).setText(String.valueOf(Math.round(totalScore)));
            ((TextView) findViewById(R.id.buildingName)).setText(buildingName);
            ((TextView) findViewById(R.id.path)).setText(pathName);
            ((TextView) findViewById(R.id.totalPathTime)).setText(String.valueOf(netTimeMinutes) + " minuti e " + String.valueOf(netTimeSeconds) + " secondi");
            ((TextView) findViewById(R.id.totalTimeProofs)).setText(String.valueOf(netTimeProofsMinutes) + " minuti e " + String.valueOf(netTimeProofsSeconds) + " secondi");
            ((TextView) findViewById(R.id.minScoreProof)).setText(String.valueOf(Math.round(result.getMinScoreProof())));
            ((TextView) findViewById(R.id.maxScoreProof)).setText(String.valueOf(Math.round(result.getMaxScoreProof())));

            setButtons();

            ListView listView = (ListView) findViewById(R.id.stepsResults);
            justifyListViewHeightBasedOnChildren(listView, numberOfResults);
            listView.setAdapter(new ResultsAdapter(this, results));
        }

    }

    // setta la dimensione della listView in base a quanti elementi ci sono nella lista
    public static void justifyListViewHeightBasedOnChildren (ListView listView, Integer rows) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = rows*250;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setButtons() {
        Button saveResult = (Button) findViewById(R.id.saveResult);
        Button rankingButton = (Button) findViewById(R.id.rankingButton);
        Button returnToHome = (Button) findViewById(R.id.returnToHome);

        if (saveResult != null) {
            saveResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LoginManager.sharedManager(getApplicationContext()).isLogged()) {
                        DataRequestMaker.saveResult(getApplicationContext(), result, new AbstractDataManagerListener<Boolean>() {
                            @Override
                            public void onResponse(Boolean response) {

                                if(response) {
                                    Toast.makeText(getApplicationContext(), "Risultato salvato", Toast.LENGTH_SHORT).show();
                                    i.setClass(getApplicationContext(), BuildingSearchActivity.class);
                                    startActivity(i);
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                                    builder.setMessage("C'è stato un errore nel salvataggio dei dati. Riprova.")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                }
                                            });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }

                            @Override
                            public void onError(ServerError error) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                                builder.setMessage("C'è stato un errore nel salvataggio dei dati. Riprova.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                Log.e(TAG, error.debugMessage);
                                Log.e(TAG, error.userMessage);
                            }
                        });
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                        builder.setMessage("Per poter salvare il risultato devi essere loggato. Desideri" +
                                " effettuare il login ora?")
                                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        i.putExtra("loginFromResult", true);
                                        i.setClass(getApplicationContext(), LoginActivity.class);
                                        onPause();
                                        startActivity(i);
                                    }
                                })
                                .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }

        if (rankingButton != null) {
            rankingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataRequestMaker.getRanking(getApplicationContext(), pathId, new AbstractDataManagerListener<Score[]>() {
                        @Override
                        public void onResponse(Score[] response) {
                            i.setClass(getApplicationContext(), RankingActivity.class);
                            i.putExtra("scores", response);
                            startActivity(i);
                        }

                        @Override
                        public void onError(ServerError error) {

                        }
                    });
                }
            });
        }

        if(returnToHome != null) {
            returnToHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i.setClass(getApplicationContext(), BuildingSearchActivity.class);
                    startActivity(i);
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("result", (Serializable) result);
        super.onSaveInstanceState(savedInstanceState);
    }

}
