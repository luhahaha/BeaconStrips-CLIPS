/**
 * @file LoginActivity.java
 * @date 2016-07-10
 * @version 1.70
 * @author Matteo Franco
 * Gestisce l'activity di login per l'utente; comprende inoltre l'activity per la password dimenticata e per il recupero
 */

package beaconstrips.clips.client.viewcontroller.authentication;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import beaconstrips.clips.client.data.LoggedUser;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.R;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.savedresults.ResultActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class LoginActivity extends MenuActivity {

    private EditText email;
    private EditText password;
    private TextInputLayout missingEmail;
    private TextInputLayout missingPassword;
    private boolean fromResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(LoginManager.sharedManager(getApplicationContext()).isLogged()) {
            Intent i = new Intent(getApplicationContext(), AccountActivity.class);
            startActivity(i);
        }
        else {
            email = (EditText) findViewById(R.id.email_login);
            password = (EditText) findViewById(R.id.password_login);
            missingEmail = (TextInputLayout) findViewById(R.id.email_label);
            missingPassword = (TextInputLayout) findViewById(R.id.password_label);
            fromResults = getIntent().getBooleanExtra("loginFromResult", false);
            setButton();
            setLoginButton();
        }
    }

    void setLoginButton() {
        Button loginButton = (Button) findViewById(R.id.login_button);
        if(loginButton != null) {
            loginButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String e = email.getText().toString();
                    String p = password.getText().toString();
                    if(checkFields(e, p)) {
                        LoginManager.sharedManager(getApplicationContext()).login(e, p, new AbstractDataManagerListener<Boolean>() {
                            @Override
                            public void onResponse(Boolean response) {
                                Log.e("funz", "response");
                                if(LoginManager.sharedManager(getApplicationContext()).isLogged()) {
                                    Log.e("LoginActivity", "loggato");
                                    LoggedUser u = LoginManager.sharedManager(getApplicationContext()).getLoggedUser();
                                    Intent i = new Intent(getApplicationContext(), AccountActivity.class);
                                    if(fromResults) {
                                        i.setClass(getApplicationContext(), ResultActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    }
                                    Toast.makeText(getApplicationContext(), "Ora sei autenticato come " + u.username,
                                            Toast.LENGTH_LONG).show();
                                    startActivity(i);
                                }

                            }

                            @Override
                            public void onError(ServerError error) {
                                // mostro un errore generico
                                Toast.makeText(getApplicationContext(), "L'email o la password inserite non sono presenti nei nostri server",
                                        Toast.LENGTH_LONG).show();
                                Log.e("funz", "error");
                            }
                        });
                    }

                }
            });
        }
    }

    boolean checkFields(String email, String password) {
        boolean e = false, p = false;
        if(TextUtils.isEmpty(email)) {
            missingEmail.setErrorEnabled(true);
            missingEmail.setError("Riempire entrambi i campi");
            e = true;
        }
        else {
            // è stato corretto l'errore quindi non lo faccio più visualizzare
            missingEmail.setErrorEnabled(false);
        }

        if(TextUtils.isEmpty(password)) {
            missingPassword.setErrorEnabled(true);
            missingPassword.setError("Riempire entrambi i campi");
            p = true;
        }
        else {
            // è stato corretto l'errore quindi non lo faccio più visualizzare
            missingPassword.setErrorEnabled(false);
        }

        if(e || p) {
            return false;
        }
        return true;
    }

    void setButton() {

        final Button forgotPassword = (Button) findViewById(R.id.forgot_password_button);
        if (forgotPassword != null) {
            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                    startActivity(i);
                }
            });
        }
    }
}
