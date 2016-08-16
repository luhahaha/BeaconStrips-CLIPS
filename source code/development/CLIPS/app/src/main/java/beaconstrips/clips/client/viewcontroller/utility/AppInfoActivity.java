/**
 @file AppInfoActivity.java
 @date 2016-07-22
 @version 1.10
 @author Viviana Alessio
 @description rappresenta l'activity contenente le informazioni dell'applicazione
 **/

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
}
