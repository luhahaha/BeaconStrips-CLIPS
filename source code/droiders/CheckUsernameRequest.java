package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 20/07/16.
 */
public class CheckUsernameRequest extends URLRequest {

   CheckUsernameRequest(Context cx, String username, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "", setBody(username), false, listener);
   }

   private static JSONObject setBody(String username) {
      JSONObject body = new JSONObject();
      try {
         body.put("username", username);
      } catch(JSONException e) {

      }
      return body;
   }
}
