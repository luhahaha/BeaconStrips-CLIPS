package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import beaconstrips.clips.client.data.LoggedUser;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file ChangeDataTest.java
 * @date 30/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU21 (Test di Unità 21). Verifica che la richiesta di modificare i dati dell'utente sia effettuata correttamente.
 *
 *
 * Stampa attesa per il test "change": "Chiamata change() eseguita con successo:"
 *                                     "Username di loggedUser: Cenze"
 *                                     "Chiamata changeNoUsername() eseguita con successo:"
 *                                     "Username di loggedUser: Cenze"
 *                                     "Chiamata changeNoPassword() eseguita con successo:"
 *                                     "Username di loggedUser: Cenze9"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ChangeDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void change() {
      final Context context = rule.getActivity().getBaseContext();
      final android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "9b444df00879");
      editor.putString("username", "Cenze");
      editor.putString("email", "cenze@gmail.com");
      editor.apply();
      LoginManager.sharedManager(context).change("Cenze", "Cenze94", "Cenze95", new AbstractDataManagerListener<Boolean>() {
         public void onResponse(Boolean response) {
            LoggedUser loggedUser = LoginManager.sharedManager(context).getLoggedUser();
            Log.d("ChangeDataTest", "Chiamata change() eseguita con successo:");
            Log.d("ChangeDataTest", "   Username di loggedUser: " + loggedUser.username);
         }

         public void onError(ServerError error) {
            Log.d("ChangeDataTest", "Rilevato un errore in change():");
            Log.d("ChangeDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ChangeDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ChangeDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void changeNoUsername() {
      final Context context = rule.getActivity().getBaseContext();
      final android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor = preferences.edit();
      editor.putString("token", "9b444df00879");
      editor.putString("username", "Cenze");
      editor.putString("email", "cenze@gmail.com");
      editor.apply();
      System.out.println(LoginManager.sharedManager(context).getLoggedUser().token);
      LoginManager.sharedManager(context).change("", "Cenze95", "Cenze94", new AbstractDataManagerListener<Boolean>() {
         public void onResponse(Boolean response) {
            LoggedUser loggedUser = LoginManager.sharedManager(context).getLoggedUser();
            Log.d("ChangeDataTest", "Chiamata changeNoUsername() eseguita con successo:");
            Log.d("ChangeDataTest", "   Username di loggedUser: " + loggedUser.username);
         }

         public void onError(ServerError error) {
            Log.d("ChangeDataTest", "Rilevato un errore in changeNoUsername():");
            Log.d("ChangeDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ChangeDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ChangeDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void changeNoPassword() {
      final Context context = rule.getActivity().getBaseContext();
      final android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor = preferences.edit();
      editor.putString("token", "9b444df00879");
      editor.putString("username", "Cenze");
      editor.putString("email", "cenze@gmail.com");
      editor.apply();
      LoginManager.sharedManager(context).change("Cenze9", "Cenze95", "", new AbstractDataManagerListener<Boolean>() {
         public void onResponse(Boolean response) {
            LoggedUser loggedUser = LoginManager.sharedManager(context).getLoggedUser();
            Log.d("ChangeDataTest", "Chiamata changeNoPassword() eseguita con successo:");
            Log.d("ChangeDataTest", "   Username di loggedUser: " + loggedUser.username);
         }
         public void onError(ServerError error) {
            Log.d("ChangeDataTest", "Rilevato un errore in changeNoPassword():");
            Log.d("ChangeDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ChangeDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ChangeDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
