/**
 * @file ResultActivity.java
 * @date 2016-07-20
 * @version 1.00
 * @author Matteo Franco
 * Si occupa di gestire l'activity dei risultati conseguiti dall'utente
 */

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //TODO add buttons linking

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
