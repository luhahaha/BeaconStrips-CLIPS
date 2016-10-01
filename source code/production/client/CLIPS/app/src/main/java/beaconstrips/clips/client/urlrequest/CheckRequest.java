package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @file CheckRequest.java
 * @date 20/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server di controllare i dati digitati dall'utente
 */
public class CheckRequest extends URLRequest{

   CheckRequest(Context cx, String email, String username, String password, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "validateFields", setBody(email, username, password), false, listener);
      execute(ResponseExpected.Object);
   }

   private static JSONObject setBody(String email, String username, String password) {
      JSONObject body = new JSONObject();
      try {
         body.put("email", email);
         body.put("username", username);
         body.put("password", password);
      } catch(JSONException e) {
         signalError();
      }
      return body;
   }
}
