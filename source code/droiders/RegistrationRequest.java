package urlrequest;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enrico on 05/07/2016.
 */
public class RegistrationRequest extends URLRequest {
   RegistrationRequest(String email, String username, String password) {
      super(Request.Method.POST, URLDataConstants.baseURL + "", setBody(email,username,password), false, new RegistrationRequestListener()); //l'url è da finire
   }

   private static JSONObject setBody(String email, String username, String password){
      JSONObject body = new JSONObject();
      try{
         body.put("email", email);
         body.put("username", username);
         body.put("password", password);
      }
      catch(JSONException e){

      }
      return body;
   }
}


class RegistrationRequestListener extends URLRequestListener {

   @Override
   public void onResponse(JSONObject response) {

   }

   @Override
   public void onError(VolleyError error) {

   }
}