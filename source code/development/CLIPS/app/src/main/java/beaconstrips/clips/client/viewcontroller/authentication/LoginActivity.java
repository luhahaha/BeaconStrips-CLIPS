package beaconstrips.clips.client.viewcontroller.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import beaconstrips.clips.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setButton();
        //TODO add check fields
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

class ForgotPassword extends AppCompatActivity {
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

class RecoverPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_recover_password);
        //TODO add what to do if error with recover
    }
}
