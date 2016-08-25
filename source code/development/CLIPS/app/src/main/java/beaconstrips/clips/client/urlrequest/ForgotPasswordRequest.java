package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @file ForgotPasswordRequest.java
 * @date 25/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per segnalare il server che l'utente ha dimenticato la propria password.
 */
public class ForgotPasswordRequest extends URLRequest {
   ForgotPasswordRequest(Context cx, String email, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "forgotPassword", setBody(email), false, listener);
      execute(URLRequest.ResponseExpected.Object);
   }

   private static JSONObject setBody(String email) {
      JSONObject body = new JSONObject();
      try {
         body.put("email", email);
      } catch (JSONException e) {
         signalError();
      }
      return body;
   }
}
