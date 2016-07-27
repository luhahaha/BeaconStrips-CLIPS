package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere l'elenco degli edifici abilitati più vicini alla posizione inviata, per ogni edificio viene inviata anche la lista dei percorsi presenti
public class BuildingsRequest extends URLRequest {

   BuildingsRequest(Context cx, double latitude, double longitude, int maxBuildings, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "buildings", setBody(latitude, longitude, maxBuildings), false, listener);
      execute();
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
