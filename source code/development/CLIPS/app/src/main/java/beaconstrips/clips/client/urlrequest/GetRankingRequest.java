package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @file GetRankingRequest.java
 * @date 29/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server della classifica del percorso selezionato
 */
public class GetRankingRequest extends URLRequest {
   GetRankingRequest(Context cx, int pathID, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "ranking", setBody(pathID), true, listener);
      execute(ResponseExpected.Array);
   }

   private static JSONObject setBody(int pathID) {
      JSONObject body = new JSONObject();
      try {
         body.put("pathID", pathID);
      } catch (JSONException e) {
         signalError();
      }
      return body;
   }
}
