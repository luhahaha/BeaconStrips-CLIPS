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
 * @file PathTest.java
 * @date 23/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU4 (Test di Unità 4). Verifica che la richiesta al server dei dati del percorso selezionato sia effettuata correttamente.
 * È previsto un unico caso possibile di errore, ovvero quando viene passato un id del percorso selezionato che non esiste; non dovrebbe accadere mai, ma una corretta gestione di questo caso garantisce una maggior solidità dell'applicazione e un aiuto per la manutenzione
 *
 *
 * Stampa attesa per il test "path": "Chiamata path() eseguita con successo"
 * Stampa attesa per il test "wrongPath:" "Rilevato un errore in wrongPath():"
 *                                        "Codice dell'errore: 461"
 *                                        "Messaggio per l'utente: null"
 *                                        "Messaggio di debug: Unable to find path with id 0"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class PathTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void path() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.getPath(context, 1, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("PathTest", "Chiamata path() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("PathTest", "Rilevato un errore in path():");
            Log.d("PathTest", "Codice dell'errore: " + error.errorCode);
            Log.d("PathTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("PathTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void wrongPath() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.getPath(context, 0, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("PathTest", "Chiamata wrongPath() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("PathTest", "Rilevato un errore in wrongPath():");
            Log.d("PathTest", "Codice dell'errore: " + error.errorCode);
            Log.d("PathTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("PathTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
