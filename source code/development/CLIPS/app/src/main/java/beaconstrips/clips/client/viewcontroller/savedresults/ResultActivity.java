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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

public class ResultActivity extends AppCompatActivity {

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //TODO add buttons linking

        i = getIntent();
        double totalScore = i.getDoubleExtra("totalScore", 0.0);
        ((TextView) findViewById(R.id.totalScore)).setText(String.valueOf(totalScore));
        /*
        DataRequestMaker.getResults(getApplicationContext(), new AbstractDataManagerListener<PathResult[]>() {
        @Override
        public void onResponse(PathResult[] response) {

        }

        @Override
        public void onError(ServerError error) {

        }
    });
    */

    }

}
