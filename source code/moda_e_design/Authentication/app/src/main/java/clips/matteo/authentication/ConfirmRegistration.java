package clips.matteo.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmRegistration extends AppCompatActivity {

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
        final Button register = (Button) findViewById(R.id.link_login_button);
        if (register != null) {
            register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
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