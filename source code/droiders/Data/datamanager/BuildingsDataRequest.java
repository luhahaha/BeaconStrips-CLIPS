package Data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Data.PathInfo;

/**
 * Created by andrea on 26/07/16.
 */
public class BuildingsDataRequest extends DataManager<Data.Building[]> {
   double latitude, longitude;
   int maxBuildings;

   BuildingsDataRequest(Context cx, double latitude, double longitude, int maxBuildings, AbstractDataManagerListener<Data.Building[]> listener) {
      super(cx, DataManager.CachePolicy.AlwaysReplaceLocal, listener);
      this.latitude = latitude;
      this.longitude = longitude;
      this.maxBuildings = maxBuildings;
      execute();
   }

   protected String queryForLocalData() {
      return "SELECT * FROM Building";
   }

   protected Data.Building[] parseFromLocal() {
      Data.Building[] buildings = new Data.Building[5];
      return buildings;
   }

   protected void getRemoteData(urlrequest.AbstractUrlRequestListener listener) {
      urlrequest.RequestMaker.getBuildings(cx, latitude, longitude, maxBuildings, listener);
   }

   protected Data.Building[] parseFromUrlRequest(JSONObject response){
      try {
         JSONArray array = response.getJSONArray("array");
         Data.Building[] buildings = new Data.Building[array.length()];
         for(int i=0; i<array.length(); i++) {
            JSONObject building = array.getJSONObject(i);
            JSONArray pathArray = building.getJSONArray("paths");
            ArrayList<PathInfo> paths = new ArrayList<Data.PathInfo>();
            for(int j=0; j<pathArray.length(); j++) {
               JSONObject path = pathArray.getJSONObject(i);
               paths.add(new Data.PathInfo(path.getInt("id"), path.getString("title"), path.getString("description"), path.getString("target"), path.getString("estimatedDuration")));
            }
            buildings[i] = new Data.Building(building.getInt("id"), building.getString("name"), building.getString("description"), building.getString("otherinfo"), building.getString("openingTime"), building.getString("address"), building.getDouble("latitude"), building.getDouble("longitude"), building.getString("telephone"), building.getString("email"), building.getString("whatsapp"), building.getString("telegram"), building.getString("twitter"), building.getString("facebook"), building.getString("websiteURL"), paths);
         }
         return buildings;
      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of Buildings request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new Data.Building[0];
      }
   }

   protected void updateLocalData(Data.Building[] data){

   }

   protected String getUpdateLocalDataQuery() {
      return "";
   }
}
