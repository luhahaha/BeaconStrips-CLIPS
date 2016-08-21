/**
 * @file ProofResultActivity.java
 * @date 2016-07-17
 * @version 1.00
 * @author Viviana Alessio
 * Si occupa dell'activity di visualizzazione del risultato della prova effettuata
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import beaconstrips.clips.R;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class ProofResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof_result);
    }
}
