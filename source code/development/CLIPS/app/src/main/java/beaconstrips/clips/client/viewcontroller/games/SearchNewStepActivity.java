/**
 * @file SearchNewStepActivity.java
 * @date 2016-07-18
 * @version 1.00
 * @author Viviana Alessio
 * Si occupa di gestire l'activity per la ricerca di un nuovo beacon
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.configuration.scan.ScanMode;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.device.BeaconRegion;
import com.kontakt.sdk.android.ble.filter.ibeacon.IBeaconFilters;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.ScanStatusListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleScanStatusListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.GameCollection;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.Step;
import beaconstrips.clips.client.data.Test;
import beaconstrips.clips.client.data.TrueFalseTest;
import beaconstrips.clips.client.pathprogress.PathProgressController;
import beaconstrips.clips.client.pathprogress.PathProgressControllerDelegate;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class SearchNewStepActivity extends MenuActivity implements PathProgressControllerDelegate {

   private ProximityManager proximityManager;
   private PathProgressController pathProgress;
   private Button startTestButton;
   private List<Step> steps;
   private int stepIndex;
   private final String TAG = "SearchNewStepActivity";
   private Intent i;
   GregorianCalendar startTime;
   int correctAnswer = 0;
   TextView hintLabel;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_search_new_step);
      startTestButton = (Button) findViewById(R.id.startTestButton);
      //setButtons();


      startTestButton.setVisibility(View.INVISIBLE);

      i = getIntent();
      Bundle bundle = i.getExtras();

      pathProgress = (PathProgressController) bundle.getSerializable("pathProgress");
      if(pathProgress != null) {
         pathProgress.setDelegate(this);
      }

      String hint = ((ArrayList<String>) i.getSerializableExtra("hints")).remove(0);

      hintLabel = ((TextView) findViewById(R.id.hintLabel));
      hintLabel.setText(hint);
      hintLabel.setVisibility(View.INVISIBLE);

      KontaktSDK.initialize(this);
      proximityManager = new ProximityManager(this);
      proximityManager.setIBeaconListener(createIBeaconListener());


      //stepIndex = i.getIntExtra("stepIndex", 0);

//      if (bundle != null) {
//         steps = (List<Step>) bundle.getSerializable("steps");
//         Log.i(TAG, "Steps is not null and it has " + steps.size() + " steps left");
//      }
//      else
//         Log.i("TAG", "Steps is null");

        /* TODO when proof beacon is found:
            TextView descriptionLabel = (TextView) findElementById(R.id.descriptionLabel);
            TextView hintLabel = (TextView) findElementById(R.id.hintLabel);
            Button startTestButton = Button findElementById(R.id.startTestButton);
            descriptionLabel.setVisibility(View.GONE);
            hintLabel.setVisibility(View.GONE);
         */


      //getProof(stepIndex);
      configureProximityManager();
      configureListeners();
      configureSpaces();
      setButtons();
      //configureFilters();
      //TODO add all algorithms for proximity
   }


   private void configureProximityManager() {
      proximityManager.configuration()
              .scanMode(ScanMode.LOW_LATENCY)
              .scanPeriod(ScanPeriod.create(TimeUnit.SECONDS.toMillis(3), TimeUnit.SECONDS.toMillis(10)))
              .activityCheckConfiguration(ActivityCheckConfiguration.MINIMAL)
              .monitoringEnabled(false);
   }

   private void configureListeners() {
      proximityManager.setIBeaconListener(createIBeaconListener());
      proximityManager.setScanStatusListener(createScanStatusListener());
   }

   private void configureSpaces() {
      IBeaconRegion region = new BeaconRegion.Builder()
              .setIdentifier("All my iBeacons")
              .setProximity(UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"))
              .build();

      proximityManager.spaces().iBeaconRegion(region);
   }

   private void configureFilters() {
      proximityManager.filters().iBeaconFilter(IBeaconFilters.newDeviceNameFilter(""));
   }

   @Override
   protected void onStart() {
      super.onStart();
      startScanning();
   }

   @Override
   protected void onStop() {
      proximityManager.stopScanning();
      super.onStop();
   }

   @Override
   protected void onDestroy() {
      proximityManager.disconnect();
      proximityManager = null;
      super.onDestroy();
   }

   private void startScanning() {
      proximityManager.connect(new OnServiceReadyListener() {
         @Override
         public void onServiceReady() {
            proximityManager.startScanning();
         }
      });
   }

   private void showToast(final String message) {
      runOnUiThread(new Runnable() {
         @Override
         public void run() {
            Toast.makeText(SearchNewStepActivity.this, message, Toast.LENGTH_SHORT).show();
         }
      });
   }

   private IBeaconListener createIBeaconListener() {
      Log.i(TAG, "Searching");

      return new SimpleIBeaconListener() {
         @Override
         public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
            //Log.i(TAG, "Searching " + ibeacon.getMajor());
           /*
            Step step = steps.get(stepIndex);
            if (ibeacon.getProximityUUID().equals(java.util.UUID.fromString(step.stopBeacon.UUID))
                    && ibeacon.getMajor() == step.stopBeacon.major
                    && ibeacon.getMinor() == step.stopBeacon.minor) { //qui va modificato il beacon da trovare!
               Log.i("Searching beacon", String.valueOf(step.stopBeacon.major));
               Log.i("Distance", "" + ibeacon.getProximity());
               showToast("Hai trovato il beacon!");
               startTestButton.setVisibility(View.VISIBLE);
             }
             */
            pathProgress.didFoundBeacon(ibeacon);
         }
      };
   }

   private ScanStatusListener createScanStatusListener() {
      return new SimpleScanStatusListener() {
         @Override
         public void onScanStart() {
            //showToast("Scanning started");
         }

         @Override
         public void onScanStop() {
            //showToast("Scanning stopped");
         }
      };
   }


   private void setButtons() {
      Button showHint = (Button) findViewById(R.id.showHint);
      showHint.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            hintLabel.setVisibility(View.VISIBLE);
         }
      });
   }


   @Override
   public void didReachProof(final Proof proof) {
      proximityManager.stopScanning(); //evita di crashare se continua lo scan; il problema è in PathProgressController
      startTestButton.setVisibility(View.VISIBLE);
      showToast("Hai trovato un beacon");
      i.putExtra("proof", proof);
      if (startTestButton != null) {
         startTestButton.setVisibility(View.VISIBLE);
         startTestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Test test = proof.test;

               if (test instanceof GameCollection) {
                  i.putExtra("gameCollection", test);
                  test = ((GameCollection) test).games.remove(0);
                  Log.i(TAG, "Test has now type " + test.getClass());
               }

               if (test instanceof MultipleChoiceTest) {
                  i.setClass(getApplicationContext(), MultipleChoiceQuizActivity.class);
                  i.putExtra("totalQuestions", ((MultipleChoiceTest) test).questions.size());
                  Log.i(TAG, "Set class MultipleChoiceQuizActivity.class");
               }
               if (test instanceof TrueFalseTest) {
                  i.setClass(getApplicationContext(), TrueFalseQuizActivity.class);
                  i.putExtra("totalQuestions", ((TrueFalseTest) test).questions.size());
                  Log.i(TAG, "Set class TrueFalseTest.class");
               }

               //TODO change all this stuff
               //Bundle bundle = new Bundle();
               //bundle.putSerializable("pathProgress", pathProgress);
               //i.putExtras(bundle);
               i.putExtra("test", test);
               i.putExtra("correctAnswer", correctAnswer);
               i.putExtra("stepIndex", stepIndex);
               startTime = new GregorianCalendar();
               i.putExtra("startTime", startTime);
               if (i != null) {
                  startActivity(i);
               }
               //TODO add spinner for loading

            }
         });
      }

   }

   @Override
   public void didRangeProximity(double percentage, String textToDisplay) {

   }

   @Override
   public void pathEnded(double totalScore) {

   }


   @Override
   public void onBackPressed() {
   }
}
