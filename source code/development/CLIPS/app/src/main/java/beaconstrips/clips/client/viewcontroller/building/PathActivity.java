/**
 * @file PathActivity.java
 * @date 2016-07-14
 * @version 1.10
 * @author Matteo Franco
 * @description gestisce l'activity del percorso
 **/

package beaconstrips.clips.client.viewcontroller.building;

import android.content.Context;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.GameCollection;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.Step;
import beaconstrips.clips.client.data.Test;
import beaconstrips.clips.client.data.TrueFalseTest;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.data.datamanager.PathDataRequest;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.games.SearchNewStepActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.SavedResultsActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class PathActivity extends MenuActivity {

   private final String TAG = "Path activity";
   Intent searchStep;


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

      searchStep = new Intent(getApplicationContext(), SearchNewStepActivity.class);
      ((TextView) findViewById(R.id.nameLabel)).setText(pathName);
      ((TextView) findViewById(R.id.description)).setText(pathDescription);
      ((TextView) findViewById(R.id.target)).setText(pathTarget);
      ((TextView) findViewById(R.id.duration)).setText(pathEstimatedDuration);

      DataRequestMaker.getPath(getApplicationContext(), pathId, new AbstractDataManagerListener<Path>() {
         @Override
         public void onResponse(Path response) {
            List <Step> steps = response.steps;
            if(steps != null) {
               Bundle bundle = new Bundle();
               bundle.putSerializable("steps", (Serializable) steps);
               searchStep.putExtras(bundle);
               searchStep.putExtra("stepIndex", 0);
            }

         }

         @Override
         public void onError(ServerError error) {

         }
      });

      setButton();

   }

   void setButton() {
      final Button startPath = (Button) findViewById(R.id.startButton);
      if (startPath != null) {
         startPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(searchStep);
            }
         });
      }
   }
}
