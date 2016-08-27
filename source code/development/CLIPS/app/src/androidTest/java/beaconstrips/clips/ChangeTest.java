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

import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file ChangeTest.java
 * @date 27/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU9 (Test di Unità 9). Verifica che la richiesta al server di login dell'utente sia effettuata correttamente.
 * Oltre al test con i dati corretti ne verrà effettuato due con dati errati, uno con un'email errata e l'altro con una password sbagliata.
 *
 *
 * Stampa attesa per il test "change": "Chiamata change() eseguita con successo"
 * Stampa attesa per il test "changeWrongEmail": "Rilevato un errore in changeWrongEmail():"
 *                                                     "Codice dell'errore: 460"
 *                                                     "Messaggio per l'utente: "
 *                                                     "Messaggio di debug: l'utente non esiste"
 * Stampa attesa per il test "changeWrongPassword": "Rilevato un errore in changeWrongPassword():"
 *                                                     "Codice dell'errore: 460"
 *                                                     "Messaggio per l'utente: "
 *                                                     "Messaggio di debug: l'utente non esiste"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ChangeTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void change() {
      Context context = rule.getActivity().getBaseContext();
      android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "bf0fe8d39cac");
      editor.apply();
      RequestMaker.changeProfileData(context, "UsernameCorretto", "Cenze94", "provaCorretta", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("ChangeTest", "Chiamata change() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("ChangeTest", "Rilevato un errore in change():");
            Log.d("ChangeTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ChangeTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ChangeTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   /*@Test
   public void changeWrongUsername() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.changeProfileData(context, "prova@gmail.com", "", "prova", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("ChangeTest", "Chiamata changeWrongEmail() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("ChangeTest", "Rilevato un errore in changeWrongEmail():");
            Log.d("ChangeTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ChangeTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ChangeTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void changeWrongPassword() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.changeProfileData(context, "p@gmail.com", "", "prova33", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("ChangeTest", "Chiamata changeWrongPassword() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("ChangeTest", "Rilevato un errore in changeWrongPassword():");
            Log.d("ChangeTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ChangeTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ChangeTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void changeWrongOldPassword() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.changeProfileData(context, "p@gmail.com", "passwordErrata", "prova33", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("ChangeTest", "Chiamata changeWrongOldPassword() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("ChangeTest", "Rilevato un errore in changeWrongOldPassword():");
            Log.d("ChangeTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ChangeTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ChangeTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }*/
}
