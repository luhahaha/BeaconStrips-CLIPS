package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import beaconstrips.clips.MainActivity;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.PathInfo;
import beaconstrips.clips.client.data.datamanager.DBHandler;

/**
 * @file BuildingDBTest.java
 * @date 29/08/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe che contiene il TU11 (Test di Unità 11). Verifica che le funzioni di lettura/scrittura/aggiornamento/eliminazione
 * del database degli oggetti Building e PathInfo funzionino.
 *
 *
 *
 *
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BuildingDBTest {

   private final String TAG = "BuildingDBTest";

   private boolean equalArrayBuildings(ArrayList<Building> a, Building[] b){

      if(a.size() != b.length){
         return false;
      }
      else {
         boolean ret = true;
         for(int i=0; i<a.size(); ++i){
            ret = ret && equalBuildings(a.get(i),b[i]);
         }
         return ret;
      }
   }

   private boolean equalBuildings(Building a, Building b){
      return Objects.equals(a.address, b.address) && Objects.equals(a.description, b.description) && Objects.equals(a.email, b.email) && a.latitude == b.latitude &&
              a.longitude == b.longitude && Objects.equals(a.facebook, b.facebook) && Objects.equals(a.name, b.name) && Objects.equals(a.openingTime, b.openingTime) &&
              Objects.equals(a.otherInfos, b.otherInfos) && Objects.equals(a.telegram, b.telegram) && Objects.equals(a.telephone, b.telephone) && Objects.equals(a.twitter, b.twitter) &&
              Objects.equals(a.whatsapp, b.whatsapp) && Objects.equals(a.websiteURL, b.websiteURL) && equalPathInfos(a.pathsInfos, b.pathsInfos, 0);
   }

   private boolean equalPathInfos(ArrayList<PathInfo> a, ArrayList<PathInfo> b, int index) {
      if (a.size() != b.size()) {
         return false;
      } else if (index < a.size()) {
         return equalPathInfo(a.get(index), b.get(index)) && equalPathInfos(a, b, index + 1);
      }else{
         return true;
      }
   }

   private boolean equalPathInfo(PathInfo a, PathInfo b){
      return a.id == b.id && Objects.equals(a.description, b.description) && Objects.equals(a.estimatedDuration, b.estimatedDuration) &&
              a.position == b.position && Objects.equals(a.target, b.target) && Objects.equals(a.title, b.title);
   }

   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void testBuildings() {
      Context cx = rule.getActivity().getBaseContext();
      cx.deleteDatabase("clipsDB");
      DBHandler db = new DBHandler(cx);

      db.deleteAllBuildings();
      db.deleteAllPaths();
      ArrayList<Building> testBuildings = db.readBuildings();
      if(testBuildings.size() == 0){
         Log.d(TAG, "Tabelle Building, PathInfo e Path vuote");
      }

      PathInfo[][] pathInfos = new PathInfo[5][5];

      for(int i=0; i<5; ++i){
         for(int j=0; j<5; ++j)
         {
            pathInfos[i][j] = new PathInfo(5*i+j,"title "+(5*i+j),"description "+(5*i+j),"target "+(5*i+j),"estimatedDuration "+(5*i+j), 5*i+j);
         }
      }
      Building[] buildings = new Building[5];
      for(int i=0; i<buildings.length; ++i){
         buildings[i] = new Building ("name "+i, "description "+i, "otherInfos "+i, "openingTime "+i, "address "+i, 15.0, 16.0, "telephone "+i, "email "+i, "whatsapp "+i, "telegram "+i, "twitter "+i, "facebook "+i, "website "+i, new ArrayList<PathInfo>(Arrays.asList(pathInfos[i])));
      }
      db.writeBuildings(buildings);
      testBuildings = db.readBuildings();

      if(testBuildings.size() != buildings.length || !equalArrayBuildings(testBuildings, buildings)){
         Log.d(TAG, "Test fallito.");
      }
      else if(equalArrayBuildings(testBuildings, buildings)){
         Log.d(TAG, "Test passato.");
      }


   }
}
