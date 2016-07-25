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
   private AbstractDataManagerListener<Boolean> listener;

   private LoginManager2(Context cx) {
      this.cx = cx;
      SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
      if(isLogged()) { //se esiste un token già salvato
         loggedUser = new LoggedUser(sp.getString("token", ""), sp.getString("email", ""), sp.getString("username", "")); //do per scontato che ci siano tutti i campi se c'è token
      }
   }

   private void sendResponse(Boolean response) {
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
      return loggedUser.token.equals("");
   }

   public void login(String email, String password, AbstractDataManagerListener<Boolean> listener) {
      if(loggedUser!=null) {
         logout(new AbstractDataManagerListener<Boolean>() { //serve per eliminare eventuali token ancora salvati
            public void onResponse(Boolean response) {}
            public void onError(Data.ServerError error) {}
         });
      }
      this.listener = listener;
      urlrequest.RequestMaker.login(cx, email, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //dentro response ci sono token, email e username
            try {
               SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
               sp.edit().putString("token", response.getString("token"));
               sp.edit().putString("email", response.getString("email"));
               sp.edit().putString("username", response.getString("username"));
               sp.edit().apply();
               sendResponse(true);
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }

         public void onError(VolleyError error) {

         }
      });
   }

   public void logout(AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.logout(cx, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
            sp.edit().remove("token");
            sp.edit().apply();
            sendResponse(true);
         }

         public void onError(VolleyError error) {
            System.out.println("error response:" + error.networkResponse.statusCode);
         }

      }); //esegue la chiamata al server, qui non mi interessa se riesce o no
   }

   public void register(String email, String username, String password, AbstractDataManagerListener<Boolean> listener) { //questo metodo è uguale a login, cambia solo il metodo chiamato in urlrequest.RequestMaker
      if(loggedUser!=null) {
         logout(new AbstractDataManagerListener<Boolean>() { //serve per eliminare eventuali token ancora salvati
            public void onResponse(Boolean response) {}
            public void onError(Data.ServerError error) {}
         });
      }
      this.listener = listener;
      urlrequest.RequestMaker.registration(cx, email, username, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //dentro response ci sono token, email e username
            try {
               SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
               sp.edit().putString("token", response.getString("token"));
               sp.edit().putString("email", response.getString("email"));
               sp.edit().putString("username", response.getString("username"));
               sp.edit().apply();
               sendResponse(true);
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }

         public void onError(VolleyError error) {

         }
      });
   }

   public void change(String username, String oldPassword, String password, AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.changeProfileData(cx, username, oldPassword, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //dentro response ci sono token, email e username
            try {
               SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
               sp.edit().remove("email");
               sp.edit().putString("email", response.getString("email"));
               sp.edit().remove("username");
               sp.edit().putString("username", response.getString("username"));
               sp.edit().apply();
               sendResponse(true);
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }

         public void onError(VolleyError error) {

         }
      });
   }

   public void check(String email, String username, String password, AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.check(cx, email, username, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
               sendResponse(true);
         }

         public void onError(VolleyError error) {

         }
      });
   }
}