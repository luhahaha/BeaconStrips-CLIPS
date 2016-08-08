package beaconstrips.clips.client.viewcontroller.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import beaconstrips.clips.R;
import beaconstrips.clips.client.viewcontroller.savedresults.ResultActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class AccountActivity extends MenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setButtonInfoProfilo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_drawer, menu);
        return true;
    }

    void setButtonInfoProfilo() {
        final Button info = (Button) findViewById(R.id.results_button);
        if (info != null) {
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
