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

   public static Building[] getBuildingsByNumber(ArrayList<Building> buildings, int maxBuildings, double userLatitude, double userLongitude){
      Location userPosition = new Location("");
      userPosition.setLatitude(userLatitude);
      userPosition.setLongitude(userLongitude);

      ArrayList<Building> ret = new ArrayList<Building>(maxBuildings);


      ret.add(0,buildings.get(0));


      for(int i=1; i<buildings.size(); ++i)
      {
         Location buildingPosition = new Location("");
         buildingPosition.setLatitude(buildings.get(i).latitude);
         buildingPosition.setLongitude(buildings.get(i).longitude);

         float distance = userPosition.distanceTo(buildingPosition);

         for(int j=0; j<maxBuildings; ++j){
            if(j >= ret.size()){
               ret.add(buildings.get(i));
               break;
            }
            else if(distance <= distanceToBuilding(userPosition, ret.get(j))){
               ret.add(j, buildings.get(i));
               break;
            }
         }
      }

      return ret.toArray(new Building[ret.size()]);
   }

   public static Building[] getBuildingsByDistance(ArrayList<Building> buildings, float maxDistance, double userLatitude, double userLongitude) {
      Location userPosition = new Location("");
      userPosition.setLatitude(userLatitude);
      userPosition.setLongitude(userLongitude);

      ArrayList<Building> ret = new ArrayList<Building>();

      for(int i=0; i<buildings.size(); ++i)
      {
         Location buildingPosition = new Location("");
         buildingPosition.setLatitude(buildings.get(i).latitude);
         buildingPosition.setLongitude(buildings.get(i).longitude);

         float distance = userPosition.distanceTo(buildingPosition);

         if(distance <= (maxDistance*1000)){
            boolean inserted = false;
            for(int j=0; j<ret.size(); ++j){
               Location tmpBuilding = new Location("");
               tmpBuilding.setLatitude(ret.get(j).latitude);
               tmpBuilding.setLongitude(ret.get(j).longitude);

               if(userPosition.distanceTo(tmpBuilding) > distance){
                  ret.add(j, buildings.get(i));
                  inserted = true;
                  break;
               }
            }
            if(!inserted){
               ret.add(buildings.get(i));
            }
         }
      }

      return ret.toArray(new Building[ret.size()]);
   }
}
