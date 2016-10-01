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
 * @file LoginTest.java
 * @date 27/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU7 (Test di Unità 7). Verifica che la richiesta al server di login dell'utente sia effettuata correttamente.
 * Oltre al test con i dati corretti ne verranno effettuati due con dati errati, uno con un'email errata e l'altro con una password sbagliata.
 *
 *
 * Stampa attesa per il test "login": "Chiamata login() eseguita con successo"
 * Stampa attesa per il test "loginWrongEmail": "Rilevato un errore in loginWrongEmail():"
 *                                                     "Codice dell'errore: 460"
 *                                                     "Messaggio per l'utente: "
 *                                                     "Messaggio di debug: l'utente non esiste"
 * Stampa attesa per il test "loginWrongPassword": "Rilevato un errore in loginWrongPassword():"
 *                                                     "Codice dell'errore: 460"
 *                                                     "Messaggio per l'utente: "
 *                                                     "Messaggio di debug: l'utente non esiste"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void login() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.login(context, "prova@gmail.com", "prova33", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("LoginTest", "Chiamata login() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("LoginTest", "Rilevato un errore in login():");
            Log.d("LoginTest", "Codice dell'errore: " + error.errorCode);
            Log.d("LoginTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("LoginTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void loginWrongEmail() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.login(context, "prova@gmail.com", "prova", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("LoginTest", "Chiamata loginWrongEmail() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("LoginTest", "Rilevato un errore in loginWrongEmail():");
            Log.d("LoginTest", "Codice dell'errore: " + error.errorCode);
            Log.d("LoginTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("LoginTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void loginWrongPassword() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.login(context, "p@gmail.com", "prova33", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("LoginTest", "Chiamata loginWrongPassword() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("LoginTest", "Rilevato un errore in loginWrongPassword():");
            Log.d("LoginTest", "Codice dell'errore: " + error.errorCode);
            Log.d("LoginTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("LoginTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
