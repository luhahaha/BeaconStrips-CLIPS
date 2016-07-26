package Data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import Data.LoggedUser;

public class LoginManager {
   private static LoginManager singleInstance;
   private LoggedUser loggedUser;
   private Context cx; //serve per poter usare SharedPreferences, la classe che permette di usare il "dizionario" con i valori di base che ci interessano
   private AbstractDataManagerListener<Boolean> listener;

   private LoginManager(Context cx) {
      this.cx = cx;
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      if(isLogged()) { //se esiste un token già salvato
         String token = preferences.getString("token", ""), email = preferences.getString("email", ""), username = preferences.getString("username", "");
         loggedUser = new LoggedUser(token, email, username); //do per scontato che ci siano tutti i campi se c'è token
      }
   }

   private void sendResponse(Boolean response, boolean initialization) { //initialization è true quando bisogna inizializzare di nuovo il loggedUser, false altrimenti
      if(initialization==true) {
         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
         String token = preferences.getString("token", ""), email = preferences.getString("email", ""), username = preferences.getString("username", "");
         loggedUser = new LoggedUser(token, email, username); //do per scontato che ci siano tutti i campi se c'è token
      }
      listener.onResponse(response);
   }

   private void sendError(Data.ServerError error) {
      listener.onError(error);
   }

   public static LoginManager sharedManager(Context cx) {
      if (singleInstance == null)
         singleInstance = new LoginManager(cx);
      return singleInstance;
   }

   LoggedUser getLoggedUser() {
      return loggedUser;
   }

   public String getToken() {return loggedUser.token;}

   public boolean isLogged() {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      String token = preferences.getString("token", "");
      return !token.equals("");
   }

   public void login(String email, String password, AbstractDataManagerListener<Boolean> listener) {
      if(loggedUser!=null) {
         logout(new AbstractDataManagerListener<Boolean>() { //serve per eliminare eventuali token ancora salvati, non mi interessa se ha successo o meno perché in locale il token viene riscritto nel caso, invece nel server dopo un tot di tempo che non viene usato il token viene eliminato
            public void onResponse(Boolean response) {}
            public void onError(Data.ServerError error) {}
         });
      }
      this.listener = listener;
      urlrequest.RequestMaker.login(cx, email, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //dentro response ci sono token, email e username
            try {
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("token", response.getString("token"));
               editor.putString("email", response.getString("email"));
               editor.putString("username", response.getString("username"));
               editor.apply();
               sendResponse(true, true);
            } catch (JSONException e) {
               sendError(new Data.ServerError(1001, "", "")); //per sicurezza, per evitare inconsistenze, anche se non dovrebbero esserci problemi, si potrebbero implementare i due messaggi successivamente o anche solo quello per l'utente. L'errore 1001 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(Data.ServerError error) {
            sendError(error);
         }
      });
   }

   public void logout(AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.logout(cx, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("token");
            editor.apply();
            sendResponse(true, false);
         }

         public void onError(Data.ServerError error) {
            sendError(error);
         }

      }); //esegue la chiamata al server, qui non mi interessa se riesce o no
   }

   public void register(String email, String username, String password, AbstractDataManagerListener<Boolean> listener) { //questo metodo è uguale a login, cambia solo il metodo chiamato in urlrequest.RequestMaker
      if(loggedUser!=null) {
         logout(new AbstractDataManagerListener<Boolean>() { //serve per eliminare eventuali token ancora salvati, non mi interessa se ha successo o meno perché in locale il token viene riscritto nel caso, invece nel server dopo un tot di tempo che non viene usato il token viene eliminato
            public void onResponse(Boolean response) {}
            public void onError(Data.ServerError error) {}
         });
      }
      this.listener = listener;
      urlrequest.RequestMaker.registration(cx, email, username, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //dentro response ci sono token, email e username
            try {
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("token", response.getString("token"));
               editor.putString("email", response.getString("email"));
               editor.putString("username", response.getString("username"));
               editor.apply();
               sendResponse(true, true);
            } catch (JSONException e) {
               sendError(new Data.ServerError(1001, "", "")); //per sicurezza, per evitare inconsistenze, anche se non dovrebbero esserci problemi, si potrebbero implementare i due messaggi successivamente o anche solo quello per l'utente. L'errore 1001 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(Data.ServerError error) {
            sendError(error);
         }
      });
   }

   public void change(String username, String oldPassword, String password, AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.changeProfileData(cx, username, oldPassword, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //dentro response ci sono token, email e username, se lo username è vuoto viene mandato quello vecchio
            try {
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
               SharedPreferences.Editor editor = preferences.edit();
               String token = preferences.getString("token", "");
               if(!token.equals(response.getString("token"))) {
                  editor.remove("token");
                  editor.putString("token", response.getString("token"));
                  editor.remove("email");
                  editor.putString("email", response.getString("email"));
                  editor.remove("username");
                  editor.putString("username", response.getString("username"));
               } else {
                  editor.remove("username");
                  editor.putString("username", response.getString("username"));
               }
               editor.apply();
               sendResponse(true, true);
            } catch (JSONException e) {
               sendError(new Data.ServerError(1001, "", "")); //per sicurezza, per evitare inconsistenze, anche se non dovrebbero esserci problemi, si potrebbero implementare i due messaggi successivamente o anche solo quello per l'utente. L'errore 1001 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(Data.ServerError error) {
            sendError(error);
         }
      });
   }

   public void check(String email, String username, String password, AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      urlrequest.RequestMaker.check(cx, email, username, password, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
               sendResponse(true, false);
         }

         public void onError(Data.ServerError error) {
            sendError(error);
         }
      });
   }
}