/**
 * @file ResultActivity.java
 * @date 2016-07-20
 * @version 1.00
 * @author Matteo Franco
 * Si occupa di gestire l'activity dei risultati conseguiti dall'utente
 */

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.content.Context;
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
import beaconstrips.clips.client.viewcontroller.utility.ResultsAdapter;
import beaconstrips.clips.client.viewcontroller.utility.risultatoProva;

public class ResultActivity extends AppCompatActivity {

    private Intent i;
    private String TAG = "ResultActivity";
    private PathResult result;
    private int pathId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //TODO add buttons linking

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

        ArrayList<ProofResult> results = pathProgress.pathProgress.getProofResults();

        long netTimeProofsSeconds = 0;

        for(int i = 0; i < results.size(); ++i) {
            netTimeProofsSeconds += TimeUnit.MILLISECONDS.toSeconds(results.get(i).getDuration());
        }

        netTimeProofsSeconds = (netTimeProofsSeconds % 60);
        long netTimeProofsMinutes = (netTimeProofsSeconds / 60);

        result = new PathResult(pathId, pathName, buildingName, startGlobalTime, finishGlobalTime, (int)totalScore, results);



        ((TextView) findViewById(R.id.totalScore)).setText(String.valueOf(totalScore));
        ((TextView) findViewById(R.id.buildingName)).setText(buildingName);
        ((TextView) findViewById(R.id.path)).setText(pathName);
        ((TextView) findViewById(R.id.totalPathTime)).setText(String.valueOf(netTimeMinutes) + " minuti e " + String.valueOf(netTimeSeconds) + " secondi");
        ((TextView) findViewById(R.id.totalTimeProofs)).setText(String.valueOf(netTimeProofsMinutes) + " minuti e " + String.valueOf(netTimeProofsSeconds) + " secondi");
        ((TextView) findViewById(R.id.minScoreProof)).setText(String.valueOf(result.getMinScoreProof()));
        ((TextView) findViewById(R.id.maxScoreProof)).setText(String.valueOf(result.getMaxScoreProof()));

        setButtons();

        ListView listView = (ListView)findViewById(R.id.stepsResults);
        ArrayList lista = getListData();
        justifyListViewHeightBasedOnChildren(listView, lista);
        listView.setAdapter(new ResultsAdapter(this, lista));

    }

    private ArrayList getListData() {
        // prova di esempio, poi questi dati verranno presi dal DB
        // è il ResultAdapter che mette gli elementi di row_result al posto giusto e dentro la list view
        // fare un for per scorrere tutte le tappe
        ArrayList<risultatoProva> results = new ArrayList<risultatoProva>();

        risultatoProva ris = new risultatoProva();
        ris.setData("10 Agosto");
        ris.setDurata("2 minuti");
        ris.setEdificio("Torre1");
        ris.setPunteggio("20");
        results.add(ris);

        risultatoProva ris2 = new risultatoProva();
        ris2.setData("10 Agosto");
        ris2.setDurata("3 minuti");
        ris2.setEdificio("Torre2");
        ris2.setPunteggio("10");
        results.add(ris2);

        // Add some more dummy data for testing
        return results;
    }


    // setta la dimensione della listView in base a quanti elementi ci sono nella lista
    public static void justifyListViewHeightBasedOnChildren (ListView listView, ArrayList lista) {
        int rows = lista.size();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = rows*250;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setButtons() {
        Button saveResult = (Button) findViewById(R.id.saveResult);
        Button rankingButton = (Button) findViewById(R.id.rankingButton);
        if (saveResult != null) {
            saveResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LoginManager.sharedManager(getApplicationContext()).isLogged()) {
                        DataRequestMaker.saveResult(getApplicationContext(), result, new AbstractDataManagerListener<Boolean>() {
                            @Override
                            public void onResponse(Boolean response) {
                                Toast.makeText(getApplicationContext(), "Risultato salvato", Toast.LENGTH_SHORT);
                                //i.setClass(getApplicationContext(), ) selezionare attività da aggiungere;
                            }

                            @Override
                            public void onError(ServerError error) {

                            }
                        });
                    } else {
                        //TODO mostra invito a login
                    }
                }
            });
        }

        if (rankingButton != null) {
            rankingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LoginManager.sharedManager(getApplicationContext()).isLogged()) {
                        DataRequestMaker.getRanking(getApplicationContext(), pathId, new AbstractDataManagerListener<Score[]>() {
                            @Override
                            public void onResponse(Score[] response) {
                                Log.i(TAG, "Scores " + response.length);
                                i.setClass(getApplicationContext(), RankingActivity.class);
                                i.putExtra("scores", response);
                                startActivity(i);
                            }

                            @Override
                            public void onError(ServerError error) {

                            }
                        });
                    } else {
                        //TODO mostra invito a login
                    }
                }
            });
        }
    }
}
