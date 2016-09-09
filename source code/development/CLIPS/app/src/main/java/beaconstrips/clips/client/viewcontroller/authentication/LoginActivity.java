/**
 * @file LoginActivity.java
 * @date 2016-07-10
 * @version 1.10
 * @author Matteo Franco
 * Gestisce l'activity di login per l'utente; comprende inoltre l'activity per la password dimenticata e per il recupero
 */

package beaconstrips.clips.client.viewcontroller.authentication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import beaconstrips.clips.client.data.LoggedUser;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.R;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.building.BuildingSearchActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class LoginActivity extends MenuActivity {

    private EditText email;
    private EditText password;
    private TextInputLayout missingEmail;
    private TextInputLayout missingPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email_login);
        password = (EditText) findViewById(R.id.password_login);
        missingEmail = (TextInputLayout) findViewById(R.id.email_label);
        missingPassword = (TextInputLayout) findViewById(R.id.password_label);
        setButton();
        setLoginButton();
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
                                    Toast.makeText(getApplicationContext(), "Ora sei loggato come " + u.username,
                                            Toast.LENGTH_LONG).show();
                                    startActivity(i);
                                }

                            }

                            @Override
                            public void onError(ServerError error) {
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

        if(TextUtils.isEmpty(password)) {
            missingPassword.setErrorEnabled(true);
            missingPassword.setError("Riempire entrambi i campi");
            p = true;
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
