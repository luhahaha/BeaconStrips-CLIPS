/**
 * @file RankingActivity.java
 * @date 2016-07-20
 * @version 1.10
 * @author Matteo Franco
 * Gestisce l'activity della classifica
 */

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Score;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.utility.RankingAdapter;
import beaconstrips.clips.client.viewcontroller.utility.risultatoProva;

public class RankingActivity extends AppCompatActivity {
    ListView listView;
    private Score[] scores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        listView = (ListView) findViewById(R.id.playerList);
        Intent i = getIntent();
        scores = (Score[]) i.getSerializableExtra("scores");
        /*
        DataRequestMaker.getRanking(getApplicationContext(), id, new AbstractDataManagerListener<Score[]>() {
            @Override
            public void onResponse(Score[] response) {

                int size = response.length;
                String[] position = new String[size];
                String[] player = new String[size];
                String[] score = new String[size];

                for(int i = 0; i < size; ++i) {
                    //TODO order position
                    position[i] = String.valueOf(response[i].position);
                    player[i] = response[i].username;
                    score[i] = String.valueOf(response[i].score);
                }

                ArrayAdapter<String> positionAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_player, R.id.positionLabel, position);
                listView.setAdapter(positionAdapter);
                ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_player, R.id.nameLabel, player);
                listView.setAdapter(playerAdapter);
                ArrayAdapter<String> scoreAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_player, R.id.totalScoreLabel, score);
                listView.setAdapter(scoreAdapter);
            }

            @Override
            public void onError(ServerError error) {

            }
        });
        */

        ListView listView = (ListView)findViewById(R.id.playerList);
        ArrayList lista = getListData();
        //justifyListViewHeightBasedOnChildren(listView, lista);
        listView.setAdapter(new RankingAdapter(this, lista));
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
    /*public static void justifyListViewHeightBasedOnChildren (ListView listView, ArrayList lista) {
        int rows = lista.size();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = rows*250;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    */
}
