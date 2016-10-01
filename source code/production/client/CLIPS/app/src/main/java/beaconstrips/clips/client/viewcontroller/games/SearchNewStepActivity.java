/**
 * @file SearchNewStepActivity.java
 * @date 2016-07-18
 * @version 2.00
 * @author Viviana Alessio
 * Si occupa di gestire l'activity per la ricerca di un nuovo beacon
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.ScanStatusListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleScanStatusListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.Test;
import beaconstrips.clips.client.pathprogress.PathProgressController;
import beaconstrips.clips.client.pathprogress.PathProgressControllerDelegate;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class SearchNewStepActivity extends MenuActivity implements PathProgressControllerDelegate {

   private ProximityManager proximityManager;
   private PathProgressController pathProgress;
   private Button startTestButton;
   private int stepIndex;
   private final String TAG = "SearchNewStepActivity";
   private Intent i;
   GregorianCalendar startTime;
   TextView hintLabel;
   private AlertDialog.Builder alert;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_search_new_step);
      startTestButton = (Button) findViewById(R.id.startTestButton);

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

      BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

      alert = createBluetoothAlert();

      if (!mBluetoothAdapter.isEnabled()) {
         alert.show();
      }

      configureProximityManager();
      configureListeners();
      configureSpaces();
      setButtons();
   }


   private void configureProximityManager() {
      proximityManager.configuration()
              .scanMode(ScanMode.LOW_POWER)
              .scanPeriod(ScanPeriod.RANGING)
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
      return new SimpleIBeaconListener() {
         @Override
         public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
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
      final Button showHint = (Button) findViewById(R.id.showHint);
      showHint.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            hintLabel.setVisibility(View.VISIBLE);
            showHint.setVisibility(View.INVISIBLE);
         }
      });
   }


   @Override
   public void didReachProof(final Proof proof) {
      Button showHint = (Button) findViewById(R.id.showHint);
      proximityManager.stopScanning(); //risparmio energetico
      startTestButton.setVisibility(View.VISIBLE);
      hintLabel.setVisibility(View.INVISIBLE);
      ((TextView) findViewById(R.id.proximityLabel)).setVisibility(View.INVISIBLE);
      if(showHint.getVisibility() == View.VISIBLE) {
         showHint.setVisibility(View.INVISIBLE);
      }
      showToast("Hai trovato il beacon");
      i.putExtra("proof", proof);
      if (startTestButton != null) {
         startTestButton.setVisibility(View.VISIBLE);
         startTestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Test test = proof.test;

               i.putExtra("test", test);
               i.removeExtra("correctAnswer");
               i.putExtra("correctAnswer", 0);
               i.putExtra("proofTitle", proof.title);
               i.putExtra("proofInstructions", proof.instructions);
               i.putExtra("stepIndex", stepIndex);
               startTime = new GregorianCalendar();
               i.putExtra("startTime", startTime);
               i.setClass(getApplicationContext(), ProofActivity.class);
               if (i != null) {
                  startActivity(i);
               }
                  }
               });
      }

   }

   @Override
   public void didRangeProximity(double percentage, String textToDisplay) {
      ((TextView) findViewById(R.id.proximityLabel)).setText("Sei al " + String.valueOf(percentage) + " %. " + textToDisplay);
   }

   @Override
   public void pathEnded(double totalScore) {

   }


   @Override
   public void onBackPressed() {
   }

   private AlertDialog.Builder createBluetoothAlert() {
      AlertDialog.Builder builder = new AlertDialog.Builder(SearchNewStepActivity.this);
      builder.setMessage("Devi attivare il Bluetooth per poter proseguire")
              .setPositiveButton("Attiva", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                    Intent settingsIntent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                    startActivity(settingsIntent);
                 }
              })
              .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                 }
              });
      return builder;
   }
}
