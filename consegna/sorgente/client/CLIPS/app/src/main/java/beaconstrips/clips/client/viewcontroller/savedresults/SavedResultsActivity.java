/**
 * @file SavedResultsActivity.java
 * @date 2016-07-21
 * @version 1.40
 * @author Matteo Franco
 * Gestisce l'activity dei risultati salvati
 */

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.utility.SavedResultsAdapter;

public class SavedResultsActivity extends AppCompatActivity {

    private ArrayList<PathResult> results = new ArrayList<PathResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_results);


        final ListView listView = (ListView)findViewById(R.id.savedResultRows);


        DataRequestMaker.getResults(getApplicationContext(), new AbstractDataManagerListener<PathResult[]>() {
            PathResult result;
            @Override
            public void onResponse(PathResult[] response) {
                for(int i=0; i<response.length; i++) {
                    result = new PathResult(response[i].pathID, response[i].pathName, response[i].buildingName,
                            response[i].startTime, response[i].endTime, response[i].totalScore, response[i].proofResults);
                    results.add(result);
                }

                // se la lista è vuota visualizzo il messaggio "Nessun risultato da visualizzare"
                if(results.size() == 0){
                    TextView noResults = (TextView)findViewById(R.id.noResults);
                    noResults.setVisibility(View.VISIBLE);
                    noResults.setText("Nessun risultato da visualizzare");
                }
                else {
                    listView.setAdapter(new SavedResultsAdapter(getApplicationContext(), results));
                }
            }

            @Override
            public void onError(ServerError error) {

            }
        });

    }

}
