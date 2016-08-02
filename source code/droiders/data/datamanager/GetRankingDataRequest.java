package data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 29/07/16.
 */
public class GetRankingDataRequest extends DataManager<data.PlayerRanking[]> {
   int pathID;

   GetRankingDataRequest(Context cx, int pathID, AbstractDataManagerListener<data.PlayerRanking[]> listener) {
      super(cx, CachePolicy.NoCache, listener);
      this.pathID = pathID;
      execute();
   }

   protected data.PlayerRanking[] parseFromLocal() {return new data.PlayerRanking[0];}

   protected void getRemoteData(urlrequest.AbstractUrlRequestListener listener) {
      urlrequest.RequestMaker.getRanking(cx, pathID, listener);
   }

   protected data.PlayerRanking[] parseFromUrlRequest(JSONObject response){
      try {
         JSONArray array = response.getJSONArray("array");
         data.PlayerRanking[] ranking = new data.PlayerRanking[array.length()];
         for(int i=0; i<array.length(); i++) {
            JSONObject playerRanking = array.getJSONObject(i);
            ranking[i] = new data.PlayerRanking(playerRanking.getString("username"), playerRanking.getInt("position"));
         }
         return ranking;
      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of GetRanking request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new data.PlayerRanking[0];
      }
   }

   protected void updateLocalData(data.PlayerRanking[] data){}
}