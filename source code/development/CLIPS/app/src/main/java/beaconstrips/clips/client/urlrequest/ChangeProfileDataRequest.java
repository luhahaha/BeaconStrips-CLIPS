package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @file AppInfoRequest.java
 * @date 20/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server di cambiare lo username e/o la password del profilo dell'utente
 */
public class ChangeProfileDataRequest extends URLRequest {

   ChangeProfileDataRequest(Context cx, String username, String oldPassword, String password, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "userData", setBody(username, oldPassword, password), true, listener); //Nota: username e password vengono modificati solo se non sono vuoti, in caso contrario viene fatta comunque la chiamata ma il server non cambia il rispettivo dato
      execute(ResponseExpected.Object);
   }

   private static JSONObject setBody(String username, String oldPassword, String password) {
      JSONObject body = new JSONObject();
      try {
         body.put("username", username);
         body.put("oldpassword", oldPassword);
         body.put("password", password);
      } catch(JSONException e) {
         signalError();
      }
      return body;
   }
}
