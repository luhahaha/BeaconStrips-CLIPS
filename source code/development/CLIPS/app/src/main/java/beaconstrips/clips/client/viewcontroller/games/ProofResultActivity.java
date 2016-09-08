/**
 * @file ProofResultActivity.java
 * @date 2016-07-17
 * @version 1.00
 * @author Viviana Alessio
 * Si occupa dell'activity di visualizzazione del risultato della prova effettuata
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import beaconstrips.clips.R;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class ProofResultActivity extends AppCompatActivity {

    private int stepIndex;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof_result);
        setButton();
        i = getIntent();
        stepIndex = i.getIntExtra("stepIndex", 0);
    }

    private void setButton() {
        Button next = (Button) findViewById(R.id.searchNextBeacon);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(getApplicationContext(), SearchNewStepActivity.class);
                i.putExtra("stepIndex", ++stepIndex);
                startActivity(i);
            }
        });
    }
}
