package beaconstrips.clips;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;

import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file ForgotPasswordDataTest.java
 * @date 28/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU (Test di Unità ). Verifica che la richiesta di cambiare la password dimenticata dell'utente sia effettuata correttamente.
 * L'output atteso è un errore perché l'invio automatico dell'email non è stato ancora implementato, di conseguenza tramite questo errore viene comunicato se la password è stata cambiata e qual è quella nuova.
 *
 * Stampa attesa per il test "forgotPassword": "Rilevato un errore in forgotPassword():"
 *                                             "Codice dell'errore: 552"
 *                                             "Messaggio per l'utente: L'opzione di invio della nuova password per email non è ancora disponibile, puoi usare la password: <password>"
 *                                             "Messaggio di debug: Sending function doesn't work yet."
 * Legenda: "<password>" indica che al suo posto dev'essere segnata la password nuova, siccome è generata automaticamente il valore ricevuto è variabile.
 */
public class ForgotPasswordDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void forgotPassword() {
      Context context = rule.getActivity().getBaseContext();
      LoginManager.sharedManager(context).forgotPassword("cenze@gmail.com", new AbstractDataManagerListener<Boolean>() {
         public void onResponse(Boolean response) {
            Log.d("ForgotPasswordDataTest", "Chiamata forgotPassword() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("ForgotPasswordDataTest", "Rilevato un errore in forgotPassword():");
            Log.d("ForgotPasswordDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("ForgotPasswordDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("ForgotPasswordDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
