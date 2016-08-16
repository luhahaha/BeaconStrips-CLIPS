/**
 @file ResultActivity.java
 @date 2016-07-20
 @version 1.00
 @author Matteo Franco
 @description si occupa di gestire l'activity dei risultati conseguiti dall'utente
 **/

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import beaconstrips.clips.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //TODO add buttons linking
    }
}
