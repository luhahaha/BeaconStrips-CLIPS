package data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.PlayerRanking;
import urlrequest.AbstractUrlRequestListener;
import urlrequest.RequestMaker;
import urlrequest.ServerError;

/**
 * Created by andrea on 29/07/16.
 */
public class GetRankingDataRequest extends DataManager<PlayerRanking[]> {
   int pathID;

   GetRankingDataRequest(Context cx, int pathID, AbstractDataManagerListener<PlayerRanking[]> listener) {
      super(cx, CachePolicy.NoCache, listener);
      this.pathID = pathID;
      execute();
   }

   protected PlayerRanking[] parseFromLocal() {return new PlayerRanking[0];}

   protected void getRemoteData(AbstractUrlRequestListener listener) {
      RequestMaker.getRanking(cx, pathID, listener);
   }

   protected PlayerRanking[] parseFromUrlRequest(JSONObject response){
      try {
         JSONArray array = response.getJSONArray("array");
         PlayerRanking[] ranking = new PlayerRanking[array.length()];
         for(int i=0; i<array.length(); i++) {
            JSONObject playerRanking = array.getJSONObject(i);
            ranking[i] = new PlayerRanking(playerRanking.getString("username"), playerRanking.getInt("position"));
         }
         return ranking;
      } catch(JSONException e) {
         listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of GetRanking request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new PlayerRanking[0];
      }
   }

   protected void updateLocalData(PlayerRanking[] data){}
}