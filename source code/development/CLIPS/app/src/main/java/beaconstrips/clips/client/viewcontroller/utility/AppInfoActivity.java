package beaconstrips.clips.client.viewcontroller.utility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.AppInfo;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

public class AppInfoActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        DataRequestMaker.getAppInfo(getApplicationContext(), new AbstractDataManagerListener<AppInfo>() {
            @Override
            public void onResponse(AppInfo response) {
                ((TextView) findViewById(R.id.appInfoLabel)).setText(response.description);
            }

            @Override
            public void onError(ServerError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu_drawer, menu);
        return true;
    }
}
