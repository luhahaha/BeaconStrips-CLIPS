package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 20/07/16.
 */
public class CheckPasswordRequest extends URLRequest {
   CheckPasswordRequest(Context cx, String password, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "", setBody(password), false, listener);
   }

   private static JSONObject setBody(String password) {
      JSONObject body = new JSONObject();
      try {
         body.put("password", password);
      } catch(JSONException e) {

      }
      return body;
   }
}
