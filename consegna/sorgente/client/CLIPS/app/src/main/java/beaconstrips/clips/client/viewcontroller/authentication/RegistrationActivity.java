/**
 * @file RegistrationActivity.java
 * @date 2016-07-12
 * @version 1.60
 * @author Matteo Franco
 * Gestisce l'activity di registrazione di un utente
 */

package beaconstrips.clips.client.viewcontroller.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.CheckResult;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.urlrequest.ServerError;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText password;
    private EditText repeatPassword;
    private TextInputLayout errorEmail;
    private TextInputLayout errorUsername;
    private TextInputLayout errorPassword;
    private TextInputLayout errorRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repeatPassword = (EditText) findViewById(R.id.repeat_password);
        errorEmail = (TextInputLayout) findViewById(R.id.email_label);
        errorUsername = (TextInputLayout) findViewById(R.id.username_label);
        errorPassword = (TextInputLayout) findViewById(R.id.password_label);
        errorRepeatPassword = (TextInputLayout) findViewById(R.id.repeat_password_label);
        setButton();
    }

    private void setButton() {
        final Button register = (Button) findViewById(R.id.register_button);
        if (register != null) {
            register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (checkFields()) {
                        final EditText email = (EditText) findViewById(R.id.email);
                        final EditText password = (EditText) findViewById(R.id.password);
                        final EditText username = (EditText) findViewById(R.id.username);
                        final EditText repeatPassword = (EditText) findViewById(R.id.repeat_password);

                        final String e = email.getText().toString();
                        final String p = password.getText().toString();
                        final String u = username.getText().toString();

                        final Intent i = new Intent(getApplicationContext(), ConfirmRegistration.class);
                        i.putExtra("email", email.getText().toString());
                        i.putExtra("password", password.getText().toString());

                        LoginManager.sharedManager(getApplicationContext()).check(e, u, p, new AbstractDataManagerListener<CheckResult[]>() {
                            @Override
                            public void onResponse(CheckResult[] response) {
                                if (response[1].isValid && response[0].isValid) {
                                    Log.i("Registration", response[1].reason);
                                    LoginManager.sharedManager(getApplicationContext()).registration(e, u, p, new AbstractDataManagerListener<Boolean>() {
                                        @Override
                                        public void onResponse(Boolean response) {
                                            Log.e("registrationActivity", "registration ok");
                                            clearFields();
                                            startActivity(i);
                                        }

                                        @Override
                                        public void onError(ServerError error) {
                                            Log.e("registrationActivity", "registration error");
                                        }
                                    });

                                    login(e, p, u);
                                } else {
                                    if(!response[1].isValid) {
                                        new AlertDialog.Builder(RegistrationActivity.this)
                                                .setTitle("Errore")
                                                .setMessage("Questo username è già stato preso")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                    }
                                    if(!response[0].isValid) {
                                        new AlertDialog.Builder(RegistrationActivity.this)
                                                .setTitle("Errore")
                                                .setMessage("Questa email è già stata usata")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                    }
                                }
                            }

                            @Override
                            public void onError(ServerError error) {

                            }
                        });

                    }
                }
            });
            final Button openLogin = (Button) findViewById(R.id.link_login_button);
            if (openLogin != null) {
                openLogin.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                });
            }
        }
    }

    private void login(final String email, final String password, final String username){
        LoginManager.sharedManager(getApplicationContext()).login(email, password, new AbstractDataManagerListener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                Log.e("funz", "response");
                if(LoginManager.sharedManager(getApplicationContext()).isLogged()) {
                    Log.e("RegistrationActivity", "loggato");
                    Intent i = new Intent(getApplicationContext(), AccountActivity.class);
                    Toast.makeText(getApplicationContext(), "Ora sei loggato come " + username,
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


    private void clearFields() {
        email.requestFocus();
        email.setText("");
        username.setText("");
        password.setText("");
        repeatPassword.setText("");
        errorEmail.setErrorEnabled(false);
        errorUsername.setErrorEnabled(false);
        errorPassword.setErrorEnabled(false);
        errorRepeatPassword.setErrorEnabled(false);
    }

    private boolean checkFields() {

        boolean registration = true;
        if (email != null && username != null && password != null && repeatPassword != null) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                errorEmail.setErrorEnabled(true);
                errorEmail.setError("L'indirizzo email non è valido");
                registration = false;
            }

            if (username.getText().toString().isEmpty()) {
                //espressione per username ([a-zA-Z0-9])+
                errorUsername.setErrorEnabled(true);
                errorUsername.setError("L'username non può essere vuoto");
                registration = false;
            }

            if (password.length() < 8) {
                errorPassword.setErrorEnabled(true);
                errorPassword.setError("La password è troppo corta");
                registration = false;
            } else {
                if (!(password.getText().toString().equals(repeatPassword.getText().toString()))) {
                    errorPassword.setErrorEnabled(true);
                    errorRepeatPassword.setErrorEnabled(true);
                    errorPassword.setError("Le due password non corrispondono");
                    errorRepeatPassword.setError("Le due password non corrispondono");
                    registration = false;
                }
            }
        }

        return registration;

    }
}

class ConfirmRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_registration);
        Intent intent = getIntent();
        String valueEmail = intent.getStringExtra("email");
        String valuePassword = intent.getStringExtra("password");
        final TextView email = (TextView) findViewById(R.id.email_text);
        final TextView password = (TextView) findViewById(R.id.password_text);
        email.setText(valueEmail);
        password.setText(valuePassword);
        setButton();
    }

    private void setButton() {
        // bottone "vai al tuo profilo"
        final Button register = (Button) findViewById(R.id.link_login_button);
        if (register != null) {
            register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO set to open profile
                    Intent i = new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(i);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        //lasciarlo vuoto per non avere nessuna azione
    }

}