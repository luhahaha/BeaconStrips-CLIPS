package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 20/07/16.
 */
public class CheckRequest extends URLRequest{

   CheckRequest(Context cx, String email, String username, String password, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "registrationFieldCheck", setBody(email, username, password), false, listener);
      execute();
   }

   private static JSONObject setBody(String email, String username, String password) {
      JSONObject body = new JSONObject();
      try {
         body.put("email", email);
         body.put("username", username);
         body.put("password", password);
      } catch(JSONException e) {

      }
      return body;
   }
}
