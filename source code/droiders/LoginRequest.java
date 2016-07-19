package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enrico on 05/07/2016.
 */
public class LoginRequest extends URLRequest {

   LoginRequest(Context cx, String email, String password, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "login", setBody(email, password), false, listener);
   }

   private static JSONObject setBody(String email, String password) {
      JSONObject body = new JSONObject();
      try {
         body.put("email", email);
         body.put("password", password);
      } catch (JSONException e) {

      }
      return body;
   }
}
