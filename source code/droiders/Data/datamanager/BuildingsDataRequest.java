package Data.datamanager;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

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
      Data.Building[] buildings = new Data.Building[5];
      /*try {

      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of Buildings request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
      }*/
      return buildings;
   }

   protected void updateLocalData(Data.Building[] data){

   }

   protected String getUpdateLocalDataQuery() {
      return "";
   }
}
