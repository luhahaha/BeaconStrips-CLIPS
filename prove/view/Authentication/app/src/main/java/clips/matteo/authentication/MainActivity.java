package clips.matteo.authentication;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        users = new ArrayList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButton();
    }

    private void setButton() {
        final Button register = (Button) findViewById(R.id.register_button);
        if (register != null) {
            register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("Event", "Button clicked");
                    checkFields();
                }
            });
        }
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

    private void checkFields() {
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText repeatPassword = (EditText) findViewById(R.id.repeat_password);
        boolean registration = true;
        if (email != null && username != null && password != null && repeatPassword != null) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                TextInputLayout errorEmail = (TextInputLayout) findViewById(R.id.email_label);
                errorEmail.setErrorEnabled(true);
                errorEmail.setError("L'indirizzo email non è valido");
                registration = false;
                Log.i("Registration email", String.valueOf(registration));
            }
            // TODO check su username già usato, lato server

            if (password.length() < 8) {
                TextInputLayout errorPassword = (TextInputLayout) findViewById(R.id.password_label);
                errorPassword.setErrorEnabled(true);
                errorPassword.setError("La password è troppo corta");
                registration = false;
                Log.i("Password length", String.valueOf(registration));
            }
            else {
                if (!password.getText().toString().equals(repeatPassword.getText().toString())) {
                    System.out.println(password.getText().toString() + " " + repeatPassword.getText().toString());
                    TextInputLayout errorPassword = (TextInputLayout) findViewById(R.id.password_label);
                    errorPassword.setErrorEnabled(true);
                    errorPassword.setError("Le due password non corrispondono");
                    TextInputLayout errorRepeatPassword = (TextInputLayout) findViewById(R.id.repeat_password_label);
                    errorRepeatPassword.setErrorEnabled(true);
                    errorRepeatPassword.setError("Le due password non corrispondono");
                    registration = false;
                    Log.i("Password match", String.valueOf(registration));
                }
            }
            if(registration)
            {
                users.add(new User(email.getText().toString(), username.getText().toString(), password.getText().toString()));
                System.out.println("Size:" + users.size());
            }
        }

    }
}
