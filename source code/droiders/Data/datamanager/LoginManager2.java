package Data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import Data.LoggedUser;

public class LoginManager2 {
   private static LoginManager2 singleInstance;
   private LoggedUser loggedUser;
   private Context cx; //serve per poter usare SharedPreferences, la classe che permette di usare il "dizionario" con i valori di base che ci interessano
   private AbstractDataManagerListener listener;

   private LoginManager2(Context cx) {
      this.cx = cx;
   }

   private void sendResponse(int response) {
      listener.onResponse(response);
   }

   private void sendError() {

   }

   public static LoginManager2 sharedManager(Context cx) {
      if (singleInstance == null)
         singleInstance = new LoginManager2(cx);
      return singleInstance;
   }

   LoggedUser getLoggedUser() {
      return loggedUser;
   }

   boolean isLogged() {
      return getToken().equals(""); //Nota: il metodo chiamato in getToken() ritorna la stringa vuota se non è stato salvato alcun token
   }

   public String getToken() {
      return cx.getSharedPreferences("LogData", Context.MODE_PRIVATE).getString("token", ""); //prelevo token dal file, la stringa vuota indica cose ritornare se il token non c'è. MODE_PRIVATE è il modo standard con cui si lavora sui fogli tramite il codice, in alternativa si può usare MODE_APPEND
   }

   public void login(String username, String password, AbstractDataManagerListener<Integer> listener) { //Nota: Integer è una classe wrapper

      String token = new String(), email = new String(); //per ora do per scontato di averli già ricevuto in input
      SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
      sp.edit().putString("token", token);
      sp.edit().putString("email", email);
      sp.edit().putString("username", username);
      sp.edit().apply();
   }

   public void logout(AbstractDataManagerListener<Integer> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.logout(cx, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            try {
               System.out.println("Ciao!");
               int value = response.getInt("value");
               if(value==0) {
                  SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
                  sp.edit().remove("token");
                  sp.edit().apply();
               }
               sendResponse(value);
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }

         public void onError(VolleyError error) {
            System.out.println("error response:" + error.networkResponse.statusCode);
         }

      }); //esegue la chiamata al server, qui non mi interessa se riesce o no
   }

   public void register(String email, String username, String password, AbstractDataManagerListener<Integer> listener) {

   }

   public void change(String username, String password, AbstractDataManagerListener<Integer> listener) {

   }

   public void check(String email, String username, String password, AbstractDataManagerListener<Integer> listener) {
      
   }
}