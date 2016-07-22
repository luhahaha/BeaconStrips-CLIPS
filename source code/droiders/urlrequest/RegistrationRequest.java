package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enrico on 05/07/2016.
 */
public class RegistrationRequest extends URLRequest {
   RegistrationRequest(Context cx, String email, String username, String password, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "newUser", setBody(email, username, password), false, listener);
   }

   private static JSONObject setBody(String email, String username, String password) {
      JSONObject body = new JSONObject();
      try {
         body.put("email", email);
         body.put("username", username);
         body.put("password", password);
      } catch (JSONException e) {

      }
      return body;
   }
}
