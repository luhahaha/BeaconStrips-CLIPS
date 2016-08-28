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
 * @file ForgotPasswordTest.java
 * @date 28/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU (Test di Unità ). Verifica che la richiesta al server di cambiare la password dimenticata dell'utente sia effettuata correttamente.
 * L'output atteso è un errore perché l'invio automatico dell'email non è stato ancora implementato, di conseguenza tramite questo errore viene comunicato se la password è stata cambiata e qual è quella nuova.
 * C'è inoltre un altro test che verifica se viene restituito l'errore corretto quando l'email inviata risulta errata.
 *
 * Stampa attesa per il test "forgotPassword": "Rilevato un errore in forgotPassword():"
 *                                             "Codice dell'errore: 552"
 *                                             "Messaggio per l'utente: L'opzione di invio della nuova password per email non è ancora disponibile, puoi usare la password: <password>"
 *                                             "Messaggio di debug: Sending function doesn't work yet."
 * Stampa attesa per il test "forgotPasswordWrongEmail": "Rilevato un errore in forgotPasswordWrongEmail():"
 *                                             "Codice dell'errore: 462"
 *                                             "Messaggio per l'utente: Nessun utente è registrato con l'indirizzo email c@gmail.com"
 *                                             "Messaggio di debug: email (c@gmail.com) not found in registered users"
 * Legenda: "<password>" indica che al suo posto dev'essere segnata la password nuova, siccome è generata automaticamente il valore ricevuto è variabile.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ForgotPasswordTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void forgotPassword() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.forgotPassword(context, "cenze@gmail.com", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("ForgotPasswordTest", "Chiamata forgotPassword() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("ForgotPasswordTest", "Rilevato un errore in forgotPassword():");
            Log.d("ForgotPasswordTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ForgotPasswordTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ForgotPasswordTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void forgotPasswordWrongEmail() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.forgotPassword(context, "c@gmail.com", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("ForgotPasswordTest", "Chiamata forgotPasswordWrongEmail() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("ForgotPasswordTest", "Rilevato un errore in forgotPasswordWrongEmail():");
            Log.d("ForgotPasswordTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ForgotPasswordTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ForgotPasswordTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
