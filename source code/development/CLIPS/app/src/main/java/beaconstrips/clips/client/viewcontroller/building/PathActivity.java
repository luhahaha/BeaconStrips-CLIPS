/**
 * @file PathActivity.java
 * @date 2016-07-14
 * @version 1.70
 * @author Matteo Franco
 * @description gestisce l'activity del percorso
 **/

package beaconstrips.clips.client.viewcontroller.building;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.pathprogress.PathProgressController;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.games.SearchNewStepActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.RankingActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class PathActivity extends MenuActivity {

   private final String TAG = "Path activity";
   Intent searchStep;
   PathProgressController pathProgress;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_path);
      searchStep = getIntent();

      int pathId = searchStep.getIntExtra("pathId", 0);
      String pathName = searchStep.getStringExtra("pathName");
      String pathDescription = searchStep.getStringExtra("pathDescription");
      String pathTarget = searchStep.getStringExtra("pathTarget");
      String pathEstimatedDuration = searchStep.getStringExtra("pathEstimatedDuration");

      searchStep.setClass(getApplicationContext(), SearchNewStepActivity.class);
      searchStep.putExtra("pathId", pathId);
      ((TextView) findViewById(R.id.nameLabel)).setText(pathName);
      ((TextView) findViewById(R.id.description)).setText(pathDescription);
      ((TextView) findViewById(R.id.target)).setText(pathTarget);
      ((TextView) findViewById(R.id.duration)).setText(pathEstimatedDuration);

      DataRequestMaker.getPath(getApplicationContext(), pathId, new AbstractDataManagerListener<Path>() {
         @Override
         public void onResponse(Path response) {
            pathProgress = new PathProgressController(response);
            Bundle bundle = new Bundle();
            bundle.putSerializable("pathProgress", pathProgress);
            searchStep.putExtras(bundle);
            List <String> hints = new ArrayList<String>();
            for(int i = 0; i < response.steps.size(); ++i) {
               hints.add(i, response.steps.get(i).helpText);
            }
            searchStep.putExtra("hints", (Serializable) hints);

         }


         @Override
         public void onError(ServerError error) {

         }
      });

      setButton(pathId);

   }

   void setButton(final int pathId) {
      final Button startPath = (Button) findViewById(R.id.startButton);
      if (startPath != null) {
         startPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               searchStep.putExtra("startGlobalTime", new GregorianCalendar());
               startActivity(searchStep);
            }
         });
      }


      final Button rankingButton = (Button) findViewById(R.id.rankingButton);
      if (rankingButton != null) {
         rankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent rank = new Intent(getApplicationContext(), RankingActivity.class);
               rank.putExtra("pathId", pathId);
               startActivity(rank);
            }
         });
      }
   }
}
