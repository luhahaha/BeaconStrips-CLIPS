package beaconstrips.clips.client.viewcontroller.utility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import beaconstrips.clips.R;

public class AppinfoProfilo extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo_profilo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_drawer, menu);
        return true;
    }
}
