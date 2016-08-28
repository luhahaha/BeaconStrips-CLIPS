/**
 * @file RankingActivity.java
 * @date 2016-07-20
 * @version 1.10
 * @author Matteo Franco
 * Gestisce l'activity della classifica
 */

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Score;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

public class RankingActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        listView = (ListView) findViewById(R.id.playerList);

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
    }

}
