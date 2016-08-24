/**
 * @file PathActivity.java
 * @date 2016-07-14
 * @version 1.10
 * @author Matteo Franco
 * @description gestisce l'activity del percorso
 **/

package beaconstrips.clips.client.viewcontroller.building;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.viewcontroller.games.SearchNewStepActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.SavedResultsActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class PathActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        Intent i = getIntent();
        int pathId = i.getIntExtra("pathId", 0);
        String valueName = i.getStringExtra("pathName");
        TextView name = (TextView) findViewById(R.id.nameLabel);
        name.setText(valueName);
        setButton();
    }

    void setButton() {
        final Button startPath = (Button) findViewById(R.id.startButton);
        if (startPath != null) {
            startPath.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), SearchNewStepActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
