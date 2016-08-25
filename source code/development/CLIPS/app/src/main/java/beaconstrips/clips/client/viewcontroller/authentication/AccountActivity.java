/**
 * @file AccountActivity.java
 * @date 2016-07-10
 * @version 1.20
 * @author Viviana Alessio
 * Si occupa di gestire l'activity dell'account dell'utente
 */



package beaconstrips.clips.client.viewcontroller.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.LoggedUser;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.viewcontroller.savedresults.ResultActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.SavedResultsActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class AccountActivity extends MenuActivity {
    private TextView email;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setButtonInfoProfilo();
        email = (TextView) findViewById(R.id.account_email);
        username = (TextView) findViewById(R.id.account_username);
        if(LoginManager.sharedManager(getApplicationContext()).isLogged()){
            LoggedUser u = LoginManager.sharedManager(getApplicationContext()).getLoggedUser();
            email.setText(u.email);
            username.setText(u.username);
        }
    }

    void setButtonInfoProfilo() {
        final Button info = (Button) findViewById(R.id.results_button);
        if (info != null) {
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), SavedResultsActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
