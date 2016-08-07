package beaconstrips.clips.client.viewcontroller.utility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import beaconstrips.clips.R;

public class AppInfoActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu_drawer, menu);
        return true;
    }
}
