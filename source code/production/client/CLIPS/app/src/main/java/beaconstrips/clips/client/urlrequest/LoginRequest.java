package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @file LoginRequest.java
 * @date 05/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server di login
 */
public class LoginRequest extends URLRequest {

   LoginRequest(Context cx, String email, String password, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "login", setBody(email, password), false, listener);
      execute(ResponseExpected.Object);
   }

   private static JSONObject setBody(String email, String password) {
      JSONObject body = new JSONObject();
      try {
         body.put("email", email);
         body.put("password", password);
      } catch (JSONException e) {
         signalError();
      }
      return body;
   }
}
