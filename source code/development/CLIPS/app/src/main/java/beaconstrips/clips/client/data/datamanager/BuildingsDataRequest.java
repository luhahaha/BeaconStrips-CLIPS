package beaconstrips.clips.client.data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.PathInfo;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file BuildingsDataRequest.java
 * @date 26/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da DataManager dove vengono implementati tutti i metodi necessari per ottenere, dal server o dal database locale, i dati relativi agli edifici più vicini all'utente
 */
public class BuildingsDataRequest extends DataManager<Building[]> {
   double latitude, longitude;
   int maxNumber;
   boolean searchByDistance;

   BuildingsDataRequest(Context cx, double latitude, double longitude, int maxNumber, boolean searchByDistance, AbstractDataManagerListener<Building[]> listener) {
      super(cx, DataManager.CachePolicy.AlwaysReplaceLocal, listener);
      this.latitude = latitude;
      this.longitude = longitude;
      this.maxNumber = maxNumber;
      this.searchByDistance = searchByDistance;
      execute();
   }

   protected Building[] parseFromLocal() {
      return new DBHandler(cx).getNearestBuildings(maxNumber, searchByDistance, latitude, longitude); //ritorna i maxBuildings edifici salvati in locale
   }

   protected void getRemoteData(AbstractUrlRequestListener listener) {
      RequestMaker.getBuildings(cx, latitude, longitude, maxNumber, searchByDistance, listener);
   }

   protected Building[] parseFromUrlRequest(JSONObject response){
      try {
         JSONArray array = response.getJSONArray("array");
         Building[] buildings = new Building[array.length()];
         for(int i=0; i<array.length(); i++) {
            JSONObject building = array.getJSONObject(i);
            JSONArray pathArray = building.getJSONArray("paths");
            ArrayList<PathInfo> paths = new ArrayList<PathInfo>();
            for(int j=0; j<pathArray.length(); j++) {
               JSONObject path = pathArray.getJSONObject(j);
               paths.add(new PathInfo(path.getInt("id"), path.getString("title"), path.getString("description"), path.getString("target"), path.getString("estimatedDuration"), path.getInt("position")));
            }
            buildings[i] = new Building(building.getString("name"), building.getString("description"), building.getString("otherInfo"), building.getString("openingTime"), building.getString("address"), building.getDouble("latitude"), building.getDouble("longitude"), building.getString("telephone"), building.getString("email"), building.getString("whatsapp"), building.getString("telegram"), building.getString("twitter"), building.getString("facebook"), building.getString("websiteURL"), paths);
         }
         return buildings;
      } catch(JSONException e) {
         listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of Buildings request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new Building[0];
      }
   }

   protected void updateLocalData(Building[] data){ //cancella i vecchi edifici salvati nel DB locale, aggiunge i nuovi
      new DBHandler(cx).updateBuildings(data);
   }
}
