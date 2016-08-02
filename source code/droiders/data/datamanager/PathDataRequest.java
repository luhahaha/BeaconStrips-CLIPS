package data.datamanager;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andrea on 27/07/16.
 */
public class PathDataRequest extends DataManager<data.Path> {
   int pathID;

   public PathDataRequest(Context cx, int pathID, AbstractDataManagerListener<data.Path> listener) {
      super(cx, DataManager.CachePolicy.AlwaysReplaceLocal, listener);
      this.pathID = pathID;
      execute();
   }

   protected data.Path parseFromLocal() {
      return new DBHandler(cx).readPath(pathID);
   }

   protected void getRemoteData(urlrequest.AbstractUrlRequestListener listener) {
      urlrequest.RequestMaker.getPath(cx, pathID, listener);
   }

   protected data.Path parseFromUrlRequest(JSONObject response) {
      data.Path path = new data.Path(pathID, "", "", new ArrayList<data.Step>());
      /*try {

      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of Path request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
      }*/
      return path;
   }

   protected void updateLocalData(data.Path data){
      new DBHandler(cx).updatePath(data);
   }
}
