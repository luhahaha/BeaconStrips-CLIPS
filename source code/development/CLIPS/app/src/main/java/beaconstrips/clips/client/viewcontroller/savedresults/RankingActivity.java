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

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ListView listView = (ListView) findViewById(R.id.playerList);
        String[] array = {"Giocatore1", "Giocatore2", "Giocatore3", "Giocatore4", "Giocatore5", "Giocatore6", "Giocatore7", "Giocatore8"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.row_player, R.id.nameLabel, array);
        listView.setAdapter(arrayAdapter);
    }

}
