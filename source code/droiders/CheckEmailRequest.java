package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 20/07/16.
 */
public class CheckEmailRequest extends URLRequest{

   CheckEmailRequest(Context cx, String email, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "", setBody(email), false, listener);
   }

   private static JSONObject setBody(String email) {
      JSONObject body = new JSONObject();
      try {
         body.put("email", email);
      } catch(JSONException e) {

      }
      return body;
   }
}
