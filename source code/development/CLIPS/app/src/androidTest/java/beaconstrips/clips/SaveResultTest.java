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

import java.util.ArrayList;
import java.util.GregorianCalendar;

import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file SaveResultTest.java
 * @date 19/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU3 (Test di Unità 3). Verifica che la richiesta al server di salvare i risultati del percorso appena svolto dall'utente funzioni correttamente.
 * Dato che la chiamata richiede parametri saranno previste due richieste errate, una in cui manca un dato tra quelli inviati e una in cui un dato è errato.
 * Questi errori non dovrebbero presentarsi durante l'esecuzione dell'applicazione, ma questi test garantiranno che, nel caso non fosse così, ad esempio a seguito di un aggiornamento, non avvengano comportamenti imprevisti.
 *
 *
 * Stampa attesa per il test "saveResult": "Chiamata saveResult() eseguita con successo"
 * Stampa attesa per il test "saveResultWrongData": "Rilevato un errore in saveResultWrongData():" \TODO: quando viene finito "saveResult"
 *                                                  "Codice dell'errore: "
 *                                                  "Messaggio per l'utente: "
 *                                                  "Messaggio di debug: "
 * Stampa attesa per il test "saveResultMissingData": "Rilevato un errore in saveResultMissingData(): " \TODO: quando viene finito "saveResult"
 *                                                    "Codice dell'errore: "
 *                                                    "Messaggio per l'utente: "
 *                                                    "Messaggio di debug: "
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SaveResultTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void saveResult() {
      ArrayList<ProofResult> proofResults = new ArrayList<>();
      proofResults.add(new ProofResult(4, new GregorianCalendar(2016, 7, 19, 10, 53, 55), new GregorianCalendar(2016, 7, 19, 11, 56, 5), 15));
      proofResults.add(new ProofResult(5, new GregorianCalendar(2016, 7, 19, 12, 3, 12), new GregorianCalendar(2016, 7, 19, 13, 22, 15), 19));
      PathResult result = new PathResult(1, "Prova", "Torre Archimede", new GregorianCalendar(2016, 7, 19, 10, 53, 24), new GregorianCalendar(2016, 7, 19, 13, 22, 15), 34, proofResults);

      Context context = rule.getActivity().getBaseContext();
      RequestMaker.saveResult(context, result, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("SaveResultTest", "Chiamata saveResult() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("SaveResultTest", "Rilevato un errore in saveResult():");
            Log.d("SaveResultTest", "Codice dell'errore: " + error.errorCode);
            Log.d("SaveResultTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("SaveResultTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void saveResultWrongData() {

   }

   @Test
   public void saveResultMissingData() {

   }
}
