package urlrequest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enrico on 05/07/2016.
 */
public class LoginRequest extends URLRequest{

   LoginRequest(Context cx, String email, String password) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "login", setBody(email,password), false, new LoginRequestListener()); //l'url è da finire
   }

   private static JSONObject setBody(String email, String password){
      JSONObject body = new JSONObject();
      try{
         body.put("email", email);
         body.put("password", password);
      }
      catch(JSONException e){

      }
      return body;
   }
}

class LoginRequestListener extends URLRequestListener{

   @Override
   public void onResponse(JSONObject response) {

   }

   @Override
   public void onError(VolleyError error) {

   }
}
