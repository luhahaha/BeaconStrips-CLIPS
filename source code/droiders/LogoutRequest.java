package urlrequest;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enrico on 05/07/2016.
 */
public class LogoutRequest extends URLRequest {
   LogoutRequest(Context cx) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "", setBody(), true, new LogoutRequestListener()); //l'url è da finire
   }

   private static JSONObject setBody() {
      JSONObject body = new JSONObject();

      //TODO metodo che ritorna l'email dell'utente loggato
      /*try{
         body.put("email", getLoggedEmail());
      }
      catch(JSONException e){

      }*/
      return body;
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
