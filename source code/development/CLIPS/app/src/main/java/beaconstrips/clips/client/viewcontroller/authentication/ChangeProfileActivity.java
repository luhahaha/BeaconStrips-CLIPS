package beaconstrips.clips.client.viewcontroller.authentication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import beaconstrips.clips.R;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

public class ChangeProfileActivity extends AppCompatActivity {

    private EditText newUsername;
    private EditText oldPassword;
    private EditText newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        newUsername = (EditText) findViewById(R.id.new_username_login);
        oldPassword = (EditText) findViewById(R.id.old_password_login);
        newPassword = (EditText) findViewById(R.id.new_password_login);
        setButton();
    }

    void setButton(){
        Button change = (Button) findViewById(R.id.change_profile_button);
        if(change != null) {
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String u = newUsername.getText().toString();
                    String op = oldPassword.getText().toString();
                    String np = newPassword.getText().toString();
                    RequestMaker.changeProfileData(getApplicationContext(), u, op, np, new AbstractUrlRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("change", "done");
                        }
                        @Override
                        public void onError(ServerError error) {
                            Log.e("change", "doesn't worked");
                        }
                    });
                }
            });
        }
    }

    // TODO: controlli interfaccia grafica
}
