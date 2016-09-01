package beaconstrips.clips.client.data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beaconstrips.clips.client.data.Score;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file GetRankingDataRequest.java
 * @date 29/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da DataManager dove vengono implementati tutti i metodi necessari per ottenere dal server la classifica del percorso selezionato
 */
public class GetRankingDataRequest extends DataManager<Score[]> {
   int pathID;

   GetRankingDataRequest(Context cx, int pathID, AbstractDataManagerListener<Score[]> listener) {
      super(cx, CachePolicy.NoCache, listener);
      this.pathID = pathID;
      execute();
   }

   protected Score[] parseFromLocal() {return new Score[0];}

   protected void getRemoteData(AbstractUrlRequestListener listener) {
      RequestMaker.getRanking(cx, pathID, listener);
   }

   protected Score[] parseFromUrlRequest(JSONObject response){
      try {
         JSONArray array = response.getJSONArray("array");
         Score[] ranking = new Score[array.length()];
         for(int i=0; i<array.length(); i++) {
            JSONObject score = array.getJSONObject(i);
            ranking[i] = new Score(score.getString("username"), i+1, score.getInt("totalScore"));
         }
         return ranking;
      } catch(JSONException e) {
         listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of GetRanking request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new Score[0];
      }
   }

   protected void updateLocalData(Score[] data){}
}