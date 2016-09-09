package beaconstrips.clips.client.data;

import android.location.Location;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @file Utility.java
 * @date 01/08/2016
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe di supporto per le altre classi
 */
public class Utility {

   public static GregorianCalendar stringToGregorianCalendar(String data){
      GregorianCalendar ret = new GregorianCalendar();
      try {
         ret.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(data));
      }
      catch(Exception e){
         Log.d("Utility", "Errore conversione data");
      }

      return ret;
   }

   public static int calculateTotalScore(List<ProofResult> proofsResults){
      Iterator<ProofResult> it = proofsResults.iterator();
      int ret = 0;

      while(it.hasNext()){
         ret += it.next().score;
      }

      return ret;
   }

   private static float distanceToBuilding(Location user, Building b){
      Location building = new Location("");
      building.setLongitude(b.longitude);
      building.setLatitude(b.latitude);

      return user.distanceTo(building);
   }

   private static Building[] insertBuilding(Building[] buildings, Building b, int index){
      System.arraycopy(buildings, index,buildings, index+1, (buildings.length-(index+1)));
      buildings[index] = b;
      return buildings;
   }

   public static Building[] getBuildingsByNumber(ArrayList<Building> buildings, int maxBuildings, double userLatitude, double userLongitude){
      Location userPosition = new Location("");
      userPosition.setLatitude(userLatitude);
      userPosition.setLongitude(userLongitude);

      ArrayList<Building> ret = new ArrayList<Building>(maxBuildings);
      ret = new ArrayList<Building>(Arrays.asList(insertBuilding(ret.toArray(new Building[ret.size()]), buildings.get(0), 0)));

      for(int i=1; i<buildings.size(); ++i)
      {
         Location buildingPosition = new Location("");
         buildingPosition.setLatitude(buildings.get(i).latitude);
         buildingPosition.setLongitude(buildings.get(i).longitude);

         float distance = userPosition.distanceTo(buildingPosition);
         boolean inserted = false;

         for(int j=0; j<ret.size(); ++j){
            if(ret.get(j) ==null && !inserted){
               ret = new ArrayList<Building>(Arrays.asList(insertBuilding(ret.toArray(new Building[ret.size()]), buildings.get(i), j)));
               break;
            }
            else if(!inserted && ret.get(j) != null && distance <= distanceToBuilding(userPosition, ret.get(j))){
               ret = new ArrayList<Building>(Arrays.asList(insertBuilding(ret.toArray(new Building[ret.size()]), buildings.get(i), j)));
               inserted = true;
               ++i;
            }
         }
      }

      if(ret.size() == 0){
         return null;
      }

      return ret.toArray(new Building[ret.size()]);
   }

   public static Building[] getBuildingsByDistance(ArrayList<Building> buildings, float maxDistance, double userLatitude, double userLongitude) {
      Location userPosition = new Location("");
      userPosition.setLatitude(userLatitude);
      userPosition.setLongitude(userLongitude);

      ArrayList<Building> ret = new ArrayList<Building>();
      ret = new ArrayList<Building>(Arrays.asList(insertBuilding(ret.toArray(new Building[ret.size()]), buildings.get(0), 0)));

      for(int i=1; i<buildings.size(); ++i)
      {
         Location buildingPosition = new Location("");
         buildingPosition.setLatitude(buildings.get(i).latitude);
         buildingPosition.setLongitude(buildings.get(i).longitude);

         float distance = userPosition.distanceTo(buildingPosition);
         boolean inserted = false;

         for(int j=0; j<ret.size(); ++j){
            if(ret.get(j) ==null && !inserted){
               ret = new ArrayList<Building>(Arrays.asList(insertBuilding(ret.toArray(new Building[ret.size()]), buildings.get(i), j)));
               break;
            }
            else if(!inserted && ret.get(j) != null && distance <= (maxDistance*1000)){
               ret = new ArrayList<Building>(Arrays.asList(insertBuilding(ret.toArray(new Building[ret.size()]), buildings.get(i), j)));
               inserted = true;
               ++i;
            }
         }
      }

      if(ret.size() == 0){
         return null;
      }

      return ret.toArray(new Building[ret.size()]);
   }
}
