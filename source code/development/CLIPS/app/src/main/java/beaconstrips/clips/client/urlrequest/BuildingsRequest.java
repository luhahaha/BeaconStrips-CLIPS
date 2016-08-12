package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @file BuildingsRequest.java
 * @date 01/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server degli edifici più vicini all'utente
 */
public class BuildingsRequest extends URLRequest {

   BuildingsRequest(Context cx, double latitude, double longitude, int maxBuildings, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "buildings", setBody(latitude, longitude, maxBuildings), false, listener);
      execute(ResponseExpected.Array);
   }

   private static JSONObject setBody(double latitude, double longitude, int maxBuildings) {
      JSONObject body = new JSONObject();
      try {
         body.put("latitude", latitude);
         body.put("longitude", longitude);
         body.put("maxResults", maxBuildings);
      } catch (JSONException e) {
         signalError();
      }

      return body;
   }
}
