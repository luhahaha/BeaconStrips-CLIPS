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
   private AbstractDataManagerListener<Data.ResponseStatus> listener;

   private LoginManager2(Context cx) {
      this.cx = cx;
   }

   private void sendResponse(Data.ResponseStatus response) {
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

   public void login(String email, String password, AbstractDataManagerListener<Data.ResponseStatus> listener) { //Nota: Integer è una classe wrapper
      this.listener = listener;
      urlrequest.RequestMaker.login(cx, email, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {

         }

         public void onError(VolleyError error) {

         }
      });

      String token = new String(), username = new String(); //per ora do per scontato di averli già ricevuto in input
      SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
      sp.edit().putString("token", token);
      sp.edit().putString("email", email);
      sp.edit().putString("username", username);
      sp.edit().apply();
   }

   public void logout(AbstractDataManagerListener<Data.ResponseStatus> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.logout(cx, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            try {
               int errorCode = response.getInt("errorCode");
               if(errorCode==200) {
                  SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
                  sp.edit().remove("token");
                  sp.edit().apply();
               }
               sendResponse(new Data.ResponseStatus(errorCode, response.getString("userMessage"), response.getString("debugMessage")));
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }

         public void onError(VolleyError error) {
            System.out.println("error response:" + error.networkResponse.statusCode);
         }

      }); //esegue la chiamata al server, qui non mi interessa se riesce o no
   }

   public void register(String email, String username, String password, AbstractDataManagerListener<Data.ResponseStatus> listener) {

   }

   public void change(String username, String password, AbstractDataManagerListener<Data.ResponseStatus> listener) {

   }

   public void check(String email, String username, String password, AbstractDataManagerListener<Data.ResponseStatus> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.check(cx, email, username, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            try {
               sendResponse(new Data.ResponseStatus(response.getInt("errorCode"), response.getString("userMessage"), response.getString("debugMessage")));
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }

         public void onError(VolleyError error) {

         }
      });
   }
}