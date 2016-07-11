package urlrequest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enrico on 05/07/2016.
 */
public class LogoutRequest extends URLRequest {
   LogoutRequest(Context cx) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "logout", null, true, new LogoutRequestListener()); //l'url è da finire
   }
}


class LogoutRequestListener extends URLRequestListener{

   @Override
   public void onResponse(JSONObject response) {

   }

   @Override
   public void onError(VolleyError error) {

   }
}
