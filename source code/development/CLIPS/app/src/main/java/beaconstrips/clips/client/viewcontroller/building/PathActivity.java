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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.data.datamanager.PathDataRequest;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.games.SearchNewStepActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.SavedResultsActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class PathActivity extends MenuActivity {

   private final String TAG = "Path activity";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_path);
      Intent i = getIntent();
      int pathId = i.getIntExtra("pathId", 0);
      String pathName = i.getStringExtra("pathName");
      String pathDescription = i.getStringExtra("pathDescription");
      String pathTarget = i.getStringExtra("pathTarget");
      String pathEstimatedDuration = i.getStringExtra("pathEstimatedDuration");
      int pathPosition = i.getIntExtra("pathPosition", 0);

      ((TextView) findViewById(R.id.nameLabel)).setText(pathName);
      ((TextView) findViewById(R.id.description)).setText(pathDescription);
      ((TextView) findViewById(R.id.target)).setText(pathTarget);
      ((TextView) findViewById(R.id.duration)).setText(pathEstimatedDuration);

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
