package beaconstrips.clips.data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import beaconstrips.clips.data.LoggedUser;
import beaconstrips.clips.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.urlrequest.RequestMaker;
import beaconstrips.clips.urlrequest.ServerError;

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

   private void sendResponse(boolean initialization) { //initialization è true quando bisogna inizializzare di nuovo il loggedUser, false altrimenti
      if(initialization==true) {
         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
         String token = preferences.getString("token", ""), email = preferences.getString("email", ""), username = preferences.getString("username", "");
         loggedUser = new LoggedUser(token, email, username); //do per scontato che ci siano tutti i campi se c'è token
      }
      listener.onResponse(true);
   }

   private void sendError(ServerError error) {
      listener.onError(error);
   }

   public static LoginManager sharedManager(Context cx) {
      if (singleInstance == null)
         singleInstance = new LoginManager(cx);
      return singleInstance;
   }

   void updateLoggedUser(String token, String email, String username) {
      if(token.equals("")) { //serve per il change, così mi basta un unico metodo per tutti i casi in cui serve reinizializzare loggedUser, se i dati non sono forniti vengono utilizzati quelli vecchi
         token = loggedUser.token;
      }
      if(email.equals("")) {
         email = loggedUser.email;
      }
      if(username.equals("")) {
         username = loggedUser.username;
      }
      loggedUser = new LoggedUser(token, email, username);
   }

   public LoggedUser getLoggedUser() {
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
            public void onError(ServerError error) {}
         });
      }
      this.listener = listener;
      RequestMaker.login(cx, email, password, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //dentro response ci sono token, email e username
            try {
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("token", response.getString("token"));
               editor.putString("email", response.getString("email"));
               editor.putString("username", response.getString("username"));
               editor.apply();
               updateLoggedUser(response.getString("token"), response.getString("email"), response.getString("username"));
               sendResponse(true);
            } catch (JSONException e) {
               sendError(new ServerError(1002, "Error on parsing the response JSON after the execution of login request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(ServerError error) {
            sendError(error);
         }
      });
   }

   public void logout(AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      RequestMaker.logout(cx, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("token");
            editor.apply();
            loggedUser = null; //se viene eseguito il logout allora questi dati non sono più validi
            sendResponse(false);
         }

         public void onError(ServerError error) {
            sendError(error);
         }
      });
   }

   public void registration(String email, String username, String password, AbstractDataManagerListener<Boolean> listener) { //questo metodo è uguale a login, cambia solo il metodo chiamato in urlrequest.RequestMaker
      if(loggedUser!=null) {
         logout(new AbstractDataManagerListener<Boolean>() { //serve per eliminare eventuali token ancora salvati, non mi interessa se ha successo o meno perché in locale il token viene riscritto nel caso, invece nel server dopo un tot di tempo che non viene usato il token viene eliminato
            public void onResponse(Boolean response) {}
            public void onError(ServerError error) {}
         });
      }
      this.listener = listener;
      RequestMaker.registration(cx, email, username, password, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //dentro response ci sono token, email e username
            try {
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("token", response.getString("token"));
               editor.putString("email", response.getString("email"));
               editor.putString("username", response.getString("username"));
               editor.apply();
               updateLoggedUser(response.getString("token"), response.getString("email"), response.getString("username"));
               sendResponse(true);
            } catch (JSONException e) {
               sendError(new ServerError(1002, "Error on parsing the response JSON after the execution of registration request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(ServerError error) {
            sendError(error);
         }
      });
   }

   public void change(String username, String oldPassword, String password, AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      RequestMaker.changeProfileData(cx, username, oldPassword, password, new AbstractUrlRequestListener() {
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
                  updateLoggedUser(response.getString("token"), response.getString("email"), response.getString("username"));
               } else {
                  editor.remove("username");
                  editor.putString("username", response.getString("username"));
                  updateLoggedUser("", "", response.getString("username"));
               }
               editor.apply();
               sendResponse(true);
            } catch (JSONException e) {
               sendError(new ServerError(1002, "Error on parsing the response JSON after the execution of change request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(ServerError error) {
            sendError(error);
         }
      });
   }

   public void check(String email, String username, String password, AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      RequestMaker.check(cx, email, username, password, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
               sendResponse(false);
         }

         public void onError(ServerError error) {
            sendError(error);
         }
      });
   }

   public void getProfileData(AbstractDataManagerListener<Boolean> listener) {
      this.listener = listener;
      RequestMaker.getProfileData(cx, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            try {
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
               SharedPreferences.Editor editor = preferences.edit();
               editor.remove("email");
               editor.putString("email", response.getString("email"));
               editor.remove("username");
               editor.putString("username", response.getString("username"));
               editor.apply();
               sendResponse(true);
            } catch(JSONException e) {
               sendError(new ServerError(1002, "Error on parsing the response JSON after the execution of getProfileData request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(ServerError error) {sendError(error);}
      });
   }
}