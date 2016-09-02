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
 * classe che contiene il TU9 (Test di Unità 9). Verifica che la richiesta al server di cambiare i dati dell'utente sia effettuata correttamente.
 * Oltre al test con i dati corretti ne verranno effettuati tre con dati errati, uno con un nuovo username errato, uno con una vecchia password sbagliata e l'ultimo con una nuova password non corretta.
 *
 *
 * Stampa attesa per il test "change": "Chiamata change() eseguita con successo"
 * Stampa attesa per il test "changeWrongUsername": "Rilevato un errore in changeWrongUsername():"
 *                                                     "Codice dell'errore: 460"
 *                                                     "Messaggio per l'utente: L'username Prova non è disponibile, scegline un altro!"
 *                                                     "Messaggio di debug: Username is not available"
 * Stampa attesa per il test "changeWrongPassword": "Rilevato un errore in changeWrongPassword():"
 *                                                     "Codice dell'errore: 461"
 *                                                     "Messaggio per l'utente: La password deve contenere almeno 6 caratteri e al massimo 16."
 *                                                     "Sono ammesse le lettere latine senza accenti (maiuscole e minuscole), i numeri e i segni ! @ # $ & ( ) - / \ _ ?"
 *                                                     "Messaggio di debug: new password is NOT valid"
 * Stampa attesa per il test "changeWrongOldPassword": "Rilevato un errore in changeWrongOldPassword():"
 *                                                     "Codice dell'errore: 461"
 *                                                     "Messaggio per l'utente: La vecchia password non corrisponde"
 *                                                     "Messaggio di debug: oldPassword not matching with stored password"
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
      editor.putString("token", "9b444df00879");
      editor.apply();
      RequestMaker.changeProfileData(context, "Nuovo Cenze", "Cenze94", "Cenze95", new AbstractUrlRequestListener() {
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

   @Test
   public void changeWrongUsername() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.changeProfileData(context, "Prova", "Cenze95", "Cenze94", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("ChangeTest", "Chiamata changeWrongUsername() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("ChangeTest", "Rilevato un errore in changeWrongUsername():");
            Log.d("ChangeTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ChangeTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ChangeTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void changeWrongPassword() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.changeProfileData(context, "Nuovo Cenze", "Cenze94", "Cenze", new AbstractUrlRequestListener() {
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
      RequestMaker.changeProfileData(context, "Nuovo Cenze", "passwordErrata", "Cenze95", new AbstractUrlRequestListener() {
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
   }
}
