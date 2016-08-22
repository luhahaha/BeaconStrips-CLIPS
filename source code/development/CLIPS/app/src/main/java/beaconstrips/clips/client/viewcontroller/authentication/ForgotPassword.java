package beaconstrips.clips.client.viewcontroller.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import beaconstrips.clips.R;

/**
 * @file ForgotPassword.java
 * @date 2016-08-20
 * @version 1.00
 * @author Viviana Alessio
 * Gestisce l'activity che prevede il recupero della propria password da parte di un utente
 */

public class ForgotPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setButton();
    }

    void setButton() {
        final Button recoverPassword = (Button) findViewById(R.id.recover_password_button);
        if (recoverPassword != null) {
            recoverPassword.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), RecoverPassword.class);
                    startActivity(i);
                }
            });
        }
    }
}
