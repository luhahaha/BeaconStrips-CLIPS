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
 * @file RegistrationTest.java
 * @date 23/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU6 (Test di Unità 6). Verifica che la richiesta al server di registrare il profilo dell'utente sia effettuata correttamente.
 *
 *
 * Stampa attesa per il test "registration": "Chiamata registration() eseguita con successo"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RegistrationTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void registration() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.check(context, "prova34@gmail.com", "Prova34", "prova34", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("CheckTest", "Chiamata registration() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("CheckTest", "Rilevato un errore in registration():");
            Log.d("CheckTest", "Codice dell'errore: " + error.errorCode);
            Log.d("CheckTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("CheckTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
