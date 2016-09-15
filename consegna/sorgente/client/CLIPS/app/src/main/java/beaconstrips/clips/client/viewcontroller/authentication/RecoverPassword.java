package beaconstrips.clips.client.viewcontroller.authentication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import beaconstrips.clips.R;

/**
 * @file RecoverPassword.java
 * @date 2016-08-20
 * @version 1.00
 * @author Viviana Alessio
 * Gestisce l'activity che prevede il recupero della propria password da parte di un utente
 */
public class RecoverPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_recover_password);
    }
}
