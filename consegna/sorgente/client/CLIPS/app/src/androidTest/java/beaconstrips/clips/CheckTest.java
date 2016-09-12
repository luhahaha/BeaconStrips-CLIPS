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
 * @file CheckTest.java
 * @date 23/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU10 (Test di Unità 10). Verifica che la richiesta al server di fare il check dei dati digitati dall'utente sia effettuata correttamente.
 * Se i 3 parametri su cui bisogna fare il check sono errati comunque viene restituito un messaggio di successo, quindi la richiesta non può provocare errori.
 *
 *
 * Stampa attesa per il test "check": "Chiamata check() eseguita con successo"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CheckTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void check() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.check(context, "prova@gmail.com", "Prova", "prova33", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("CheckTest", "Chiamata check() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("CheckTest", "Rilevato un errore in check():");
            Log.d("CheckTest", "Codice dell'errore: " + error.errorCode);
            Log.d("CheckTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("CheckTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
