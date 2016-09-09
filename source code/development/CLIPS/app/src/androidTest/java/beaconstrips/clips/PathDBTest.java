package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import beaconstrips.clips.MainActivity;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.Step;
import beaconstrips.clips.client.data.Beacon;
import beaconstrips.clips.client.data.Proximity;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.datamanager.DBHandler;

/**
 * @file PathDBTest.java
 * @date 29/08/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe che contiene il TU11 (Test di Unità 11). Verifica che le funzioni di lettura/scrittura/aggiornamento/eliminazione
 * del database degli oggetti Path,Beacon,Proof,Proximity,Step funzionino.
 *
 *
 *
 *
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class PathDBTest {

   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   private final String TAG = "PathDBTest";

   private boolean equalBeacons(Beacon[] a, ArrayList<Beacon> b){
      if(a.length != b.size())
      {
         return false;
      }
      for(int i=0; i<a.length; ++i){
         if(!equalBeacon(a[i], b.get(i))){
            return false;
         }
      }
      return true;
   }

   private boolean equalProximity(Proximity a, Proximity b){
      return a.percentage == b.percentage && a.textToDisplay.equals(b.textToDisplay) && equalBeacon(a.beacon, b.beacon);
   }

   private boolean equalProximities(ArrayList<Proximity> a, ArrayList<Proximity> b){
      if(a.size() != b.size()){
         return false;
      }
      for(int i=0; i<a.size(); ++i){
         if(!equalProximity(a.get(i), b.get(i))){
            return false;
         }
      }
      return true;
   }

   private boolean equalProof(Proof a, Proof b){
      return a.id == b.id && a.instructions.equals(b.instructions) && a.title.equals(b.title);
   }

   private boolean equalBeacon(Beacon a, Beacon b){
      return a.id == b.id && a.major == b.major && a.minor == b.minor && a.UUID.equals(b.UUID);
   }

   private boolean equalStep(Step a, Step b){
      return equalBeacon(a.stopBeacon, b.stopBeacon) && equalProof(a.proof, b.proof) && equalProximities(a.proximities, b.proximities);
   }

   private boolean equalSteps(ArrayList<Step> a, ArrayList<Step> b){
      if(a.size() != b.size()){
         return false;
      }
      for(int i=0; i<a.size(); ++i){
         if(!equalStep(a.get(i), b.get(i))){
            return false;
         }
      }
      return true;
   }

   private boolean equalPath(Path a, Path b){
      return a.id == b.id && a.rewardMessage.equals(b.rewardMessage) && a.startingMessage.equals(b.startingMessage) && equalSteps(a.steps, b.steps);
   }

   private boolean equalPaths(Path[] a, ArrayList<Path> b){
      if(a.length != b.size()){
         return false;
      }
      for(int i=0; i<a.length; ++i){
         if(!equalPath(a[i], b.get(i))){
            return false;
         }
      }
      return true;
   }

   @Test
   public void testPaths() {
      Context cx = rule.getActivity().getBaseContext();
      cx.deleteDatabase("clipsDB");
      DBHandler db = new DBHandler(cx);

      db.deleteAllPaths();
      db.deleteAllSteps();
      db.deleteAllProximities();
      db.deleteAllBeacons();
      db.deleteAllProofs();

      ArrayList<Proof> testProofs = db.readProofs();
      ArrayList<Beacon> testBeacons = db.readBeacons();
      ArrayList<Step> testSteps = db.readAllSteps();
      ArrayList<Proximity> testProximities = db.readAllProximities();
      ArrayList<Path> testPaths = db.readPaths();
      if(testProofs.size() == 0 && testBeacons.size() == 0 && testSteps.size() == 0 && testProximities.size() == 0 && testPaths.size() == 0){
         Log.d(TAG, "Tabelle Proof, Beacon, Step, Proximity, Path vuote");
      }

      Path[] paths = new Path[5];
      Beacon[] beacons = new Beacon[10];
      Proof[] proofs = new Proof[10];
      for(int i = 0; i< beacons.length; ++i){
         beacons[i] = new Beacon(i,"123456", i, 20-i);
         try{
            proofs[i] = new Proof(i, "title "+i, "instructions "+i, new JSONObject("{\"algorithmData\":\""+i+"\"}"), new JSONObject("{\"testData\":"+i+"\"}"));
         }
         catch(JSONException e){
            Log.d(TAG, "Errore conversione JSON");
         }
      }
      for(int i=0; i<paths.length; ++i){
         ArrayList<Step> steps = new ArrayList<>();
         for(int j=0; j<2; ++j){
            ArrayList<Proximity> proximities = new ArrayList<>();
            proximities.add(new Proximity(beacons[((2*i+j)+1)%10], 33, "textToDisplay "+((2*i+j)+1)%10));
            steps.add(new Step(beacons[2*i+j], proximities, proofs[2*i+j]));
         }
         paths[i] = new Path(i,"startingMessage "+i,"rewardMessage "+i, steps);
      }

      db.writeBeacons(beacons);

      db.writePaths(paths);

      testBeacons = db.readBeacons();
      testPaths = db.readPaths();

      if(equalBeacons(beacons, testBeacons) && equalPaths(paths, testPaths)){
         Log.d(TAG, "Test passato.");
      }
      else{
         Log.d(TAG, "Test fallito.");
      }
   }

}
