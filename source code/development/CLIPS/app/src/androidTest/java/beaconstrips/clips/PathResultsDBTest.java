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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import beaconstrips.clips.MainActivity;
import beaconstrips.clips.client.data.Beacon;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.PathInfo;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.Proximity;
import beaconstrips.clips.client.data.Step;
import beaconstrips.clips.client.data.datamanager.DBHandler;

/**
 * @file PathResultsDBTest.java
 * @date 29/08/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe che contiene il TU11 (Test di Unità 11). Verifica che le funzioni di lettura/scrittura/aggiornamento/eliminazione
 * del database degli oggetti PathResults, ProofResult funzionino.
 *
 *
 *
 *
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class PathResultsDBTest {
   private final String TAG = "PathResultsDBTest";

   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);


   private boolean equalProofResult(ProofResult a, ProofResult b){
      return a.endTime.equals(b.endTime) && a.startTime.equals(b.startTime) && a.id == b.id && a.score == b.score;
   }

   private boolean equalProofResults(ArrayList<ProofResult> a, ArrayList<ProofResult> b){
      if(a.size() != b.size()){
         return false;
      }
      for(int i=0; i<a.size(); ++i){
         if(!equalProofResult(a.get(i), b.get(i))){
            return false;
         }
      }
      return true;
   }

   private boolean equalPathResult(PathResult a, PathResult b){
      return a.buildingName.equals(b.buildingName) && a.endTime.equals(b.endTime) && a.startTime.equals(b.startTime) &&
              a.pathID == b.pathID && a.pathName.equals(b.pathName) && a.totalScore == b.totalScore && equalProofResults(a.proofResults, b.proofResults);
   }

   private boolean equalPathResults(PathResult[] a, PathResult[] b){
      if(a.length != b.length){
         return false;
      }
      for(int i=0; i<a.length; ++i){
         if(!equalPathResult(a[i], b[i])){
            return false;
         }
      }
      return true;
   }

   @Test
   public void testPathResults() {
      Context cx = rule.getActivity().getBaseContext();
      cx.deleteDatabase("clipsDB");
      DBHandler db = new DBHandler(cx);

      db.deletePathResults();
      db.deleteAllPaths();
      db.deleteAllProofs();

      PathResult[] testPathResult = db.readPathResults();
      if(testPathResult.length == 0){
         Log.d(TAG, "Tabelle PathResult e ProofResult svuotate");
      }

      int size = 5;
      Path[] paths = new Path[size];
      Proof[] proofs = new Proof[2*size];
      Beacon[] beacons = new Beacon[2*size];
      PathResult[] pathResults = new PathResult[size];


      PathInfo[][] pathInfos = new PathInfo[5][5];

      for(int i=0; i<5; ++i){
         for(int j=0; j<5; ++j)
         {
            pathInfos[i][j] = new PathInfo(5*i+j,"piTitle "+(5*i+j),"piDescription "+(5*i+j),"piTarget "+(5*i+j),"piEstimatedDuration "+(5*i+j), 5*i+j);
         }
      }
      Building[] buildings = new Building[5];
      for(int i=0; i<buildings.length; ++i){
         buildings[i] = new Building ("buildingName "+i, "buildingDescription "+i, "otherInfos "+i, "openingTime "+i, "address "+i, 15.0, 16.0, "telephone "+i, "email "+i, "whatsapp "+i, "telegram "+i, "twitter "+i, "facebook "+i, "website "+i, new ArrayList<PathInfo>(Arrays.asList(pathInfos[i])));
      }
      db.writeBuildings(buildings);



      for(int i = 0; i< beacons.length; ++i){
         beacons[i] = new Beacon(i,"123456", i, 20-i);
         try{
            proofs[i] = new Proof(i, "title "+i, "instructions "+i, new JSONObject("{\"algorithmData\":\""+i+"\"}"), new JSONObject("{\"testData\":"+i+"\"}"));
         }
         catch(JSONException e){
            Log.d(TAG, "Errore conversione JSON");
         }
      }
      for(int i=0; i<size; ++i){
         ArrayList<Step> steps = new ArrayList<>();
         ArrayList<ProofResult> proofResults = new ArrayList<>();
         GregorianCalendar startTime = new GregorianCalendar();
         GregorianCalendar endTime = new GregorianCalendar();
         String sDate = "2016-09-05T12:34:56.000Z";
         String eDate = "2016-09-06T12:34:56.000Z";
         try{
            startTime.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(sDate));
            endTime.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(eDate));
         }
         catch(Exception e){
            Log.d(TAG, "Errore conversione data");
         }
         for(int j=0; j<2; ++j){
            ArrayList<Proximity> proximities = new ArrayList<>();
            proximities.add(new Proximity(beacons[((2*i+j)+1)%(2*size)], 33, "textToDisplay "+((2*i+j)+1)%(2*size)));
            steps.add(new Step(beacons[2*i+j], proximities, proofs[2*i+j]));
            proofResults.add(new ProofResult(2*i+j, startTime, endTime, 50));
         }
         paths[i] = new Path(i,"startingMessage "+i,"rewardMessage "+i, steps);
         pathResults[i] = new PathResult(i, "piTitle "+i, "buildingName 0", startTime, endTime, 100, proofResults);
      }

      db.writeBeacons(beacons);
      db.writePaths(paths);
      db.writePathResults(pathResults);

      PathResult[] testPathResults = db.readPathResults();

      if(!equalPathResults(testPathResults, pathResults)){
         Log.d(TAG, "Test fallito.");
      }
      else{
         Log.d(TAG, "Test passato.");
      }
   }
}
