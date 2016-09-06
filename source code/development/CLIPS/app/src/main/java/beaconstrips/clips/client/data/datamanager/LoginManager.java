package beaconstrips.clips.client.data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import beaconstrips.clips.client.data.LoggedUser;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file LoginManager.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe dove sono definite tutte le operazioni necessarie per la gestione del profilo dell'utente, fra cui il login, il logout, la modifica dei dati, la registrazione e il controllo dei dati
 */
public class LoginManager {
   private static LoginManager singleInstance;
   private LoggedUser loggedUser;
   private Context cx;

   private LoginManager(Context cx) {
      this.cx = cx;
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      if(isLogged()) { //se esiste un token già salvato
         String token = preferences.getString("token", ""), email = preferences.getString("email", ""), username = preferences.getString("username", "");
         loggedUser = new LoggedUser(token, email, username); //assumo che ci siano tutti i campi se c'è token
      }
   }

   public static LoginManager sharedManager(Context cx) {
      if (singleInstance == null)
         singleInstance = new LoginManager(cx);
      return singleInstance;
   }

   public LoggedUser getLoggedUser() {
      return loggedUser;
   }

   public String getToken() {
      if(isLogged()) {
         return loggedUser.token;
      }
      return "";
   }

   public boolean isLogged() {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      String token = preferences.getString("token", "");
      return !token.equals("");
   }

   public void login(String email, String password, final AbstractDataManagerListener<Boolean> listener) {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      if(loggedUser!=null) {
         logout(new AbstractDataManagerListener<Boolean>() { //serve per eliminare eventuali token ancora salvati, non mi interessa se ha successo o meno perché in locale il token viene riscritto nel caso, invece nel server dopo un tot di tempo che non viene usato il token viene eliminato
            public void onResponse(Boolean response) {}
            public void onError(ServerError error) {}
         });
      }
      RequestMaker.login(cx, email, password, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            try {
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("token", response.getString("token"));
               editor.putString("email", response.getJSONObject("userData").getString("email"));
               editor.putString("username", response.getJSONObject("userData").getString("username"));
               editor.apply();
               loggedUser = new LoggedUser(response.getString("token"), response.getJSONObject("userData").getString("email"), response.getJSONObject("userData").getString("username"));
               listener.onResponse(true);
            } catch (JSONException e) {
               listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of login request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(ServerError error) {
            listener.onError(error);
         }
      });
   }

   public void logout(final AbstractDataManagerListener<Boolean> listener) {
      RequestMaker.logout(cx, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx); //il logout in locale viene eseguito a prescindere dalla riuscita o meno della chiamata al server
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("token");
            editor.apply();
            loggedUser = null; //se viene eseguito il logout allora questi dati non sono più validi
            listener.onResponse(true);
         }

         public void onError(ServerError error) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx); //il logout in locale viene eseguito a prescindere dalla riuscita o meno della chiamata al server
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("token");
            editor.apply();
            loggedUser = null; //se viene eseguito il logout allora questi dati non sono più validi
            listener.onError(error);
         }
      });
   }

   public void registration(String email, String username, String password, final AbstractDataManagerListener<Boolean> listener) {
      if(loggedUser!=null) {
         logout(new AbstractDataManagerListener<Boolean>() { //serve per eliminare eventuali token ancora salvati, non mi interessa se ha successo o meno perché in locale il token viene riscritto nel caso, invece nel server dopo un tot di tempo che non viene usato il token viene eliminato
            public void onResponse(Boolean response) {}
            public void onError(ServerError error) {}
         });
      }
      RequestMaker.registration(cx, email, username, password, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            int errorCode = response.optInt("errorCode");
            if(errorCode == 0) {
               try {
                  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
                  SharedPreferences.Editor editor = preferences.edit();
                  editor.putString("token", response.getString("token"));
                  editor.putString("email", response.getJSONObject("userData").getString("email"));
                  editor.putString("username", response.getJSONObject("userData").getString("username"));
                  editor.apply();
                  loggedUser = new LoggedUser(response.getString("token"), response.getJSONObject("userData").getString("email"), response.getJSONObject("userData").getString("username"));
                  listener.onResponse(true);
               } catch (JSONException e) {
                  listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of registration request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
               }
            }
            else {
               try {
                  listener.onError(new ServerError(errorCode, response.getString("debugError"), ""));
               } catch (JSONException e) {
                  listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of registration request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
               }
            }
         }

         public void onError(ServerError error) {
            listener.onError(error);
         }
      });
   }

   public void change(String username, String oldPassword, String password, final AbstractDataManagerListener<Boolean> listener) {
      RequestMaker.changeProfileData(cx, username, oldPassword, password, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            try {
               if(!response.optString("username").equals("")) {//se viene cambiata solo la password non viene restituito lo username
                  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
                  SharedPreferences.Editor editor = preferences.edit();
                  editor.remove("username");
                  editor.putString("username", response.getString("username"));
                  loggedUser = new LoggedUser(loggedUser.token, loggedUser.email, response.getString("username"));
                  editor.apply();
               } else {
                  loggedUser = new LoggedUser(loggedUser.token, loggedUser.email, loggedUser.username);
               }
               listener.onResponse(true);
            } catch (JSONException e) {
               listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of change request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(ServerError error) {
            listener.onError(error);
         }
      });
   }

   public void check(String email, String username, String password, final AbstractDataManagerListener<CheckResult[]> listener) {
      RequestMaker.check(cx, email, username, password, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            try {
               CheckResult[] checks = new CheckResult[3];
               JSONObject email = response.getJSONObject("email");
               checks[0] = new CheckResult("email", email.getBoolean("isValid"), email.optString("reason"), email.optString("debugMessage"));
               JSONObject username = response.getJSONObject("username");
               checks[1] = new CheckResult("username", username.getBoolean("isValid"), username.optString("reason"), username.optString("debugMessage"));
               JSONObject password = response.getJSONObject("password");
               checks[2] = new CheckResult("password", password.getBoolean("isValid"), password.optString("reason"), password.optString("debugMessage"));
               listener.onResponse(checks);
            } catch (JSONException e) {
               listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of change request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(ServerError error) {
            listener.onError(error);
         }
      });
   }

   public void getProfileData(final AbstractDataManagerListener<Boolean> listener) {
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
               loggedUser = new LoggedUser(preferences.getString("token", ""), response.getString("email"), response.getString("username"));
               listener.onResponse(true);
            } catch(JSONException e) {
               listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of getProfileData request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta
            }
         }

         public void onError(ServerError error) {listener.onError(error);}
      });
   }

   public void forgotPassword(String email, final AbstractDataManagerListener<Boolean> listener) {
      RequestMaker.forgotPassword(cx, email, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
               listener.onResponse(true);
         }

         public void onError(ServerError error) {listener.onError(error);}
      });
   }
}
