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
 * Dato che la richiesta è molto lenta in caso di successo il primo test aspetta due secondi in più prima di chiudersi, in questo modo dovrebbe arrivare l'esito senza problemi.
 *
 * Stampa attesa per il test "forgotPassword": "Chiamata forgotPassword() eseguita con successo"
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
      try {
         Thread.sleep(2000);
      } catch(InterruptedException e) {
         Log.d("ForgotPasswordTest", "Errore con l'attesa forzata del test, il messaggio di riuscita del test potrebbe non essere arrivato");
      }
   }
}
