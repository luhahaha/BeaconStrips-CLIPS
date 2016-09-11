package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file SaveResultDataTest.java
 * @date 27/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU16 (Test di Unità 16). Verifica che la richiesta di salvare i risultati del percorso appena svolto dall'utente funzioni correttamente.
 *
 *
 * Stampa attesa per il test "saveResult": "Chiamata getResults() eseguita con successo:"
 * Stampa attesa per il test "saveResult": "<Risultati vecchi>"
 *                                         "Chiamata saveResult() eseguita con successo"
 *                                         "Chiamata getResults() eseguita con successo:"
 *                                         "<Risultati vecchi>"
 *                                         "   ID del percorso: 1"
 *                                         "   Nome del percorso: Prova"
 *                                         "   Nome dell'edificio: Torre Archimede"
 *                                         "   Orario d'inizio: 2016-08-19T10:53:24.000Z"
 *                                         "   Orario di fine: 2016-08-19T13:22:15.000Z"
 *                                         "   Punteggio finale: 34"
 *                                         "   Prova 0:"
 *                                         "      ID della tappa: 4"
 *                                         "      Orario d'inizio: 2016-08-24T10:53:55.000Z"
 *                                         "      Orario di fine: 2016-08-24T11:56:05.000Z"
 *                                         "      Punteggio della tappa: 15"
 *                                         "   Prova 1:"
 *                                         "      ID della tappa: 5"
 *                                         "      Orario d'inizio: 2016-08-24T12:03:12.000Z"
 *                                         "      Orario di fine: 2016-08-24T13:22:15.000Z"
 *                                         "      Punteggio della tappa: 19"
 *                                         "<Risultati vecchi>"
 * Legenda: "<Risultati vecchi>" indica che vengono rappresentati alcuni o tutti i risultati dell'utente, quello che si vuole indicare in questo modo è che bisogna che tra prima e dopo la richiesta di salvataggio del risultato vengano stampate in più le stringhe rappresentate sopra.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SaveResultDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void saveResult() {
      ArrayList<ProofResult> proofResults = new ArrayList<>();
      proofResults.add(new ProofResult(6, new GregorianCalendar(2016, 7, 19, 10, 53, 55), new GregorianCalendar(2016, 7, 19, 11, 56, 5), 33));
      proofResults.add(new ProofResult(7, new GregorianCalendar(2016, 7, 19, 12, 3, 12), new GregorianCalendar(2016, 7, 19, 13, 22, 15), 33));
      PathResult result = new PathResult(1, "Prova", "Torre Archimede", new GregorianCalendar(2016, 7, 19, 10, 53, 24), new GregorianCalendar(2016, 7, 19, 13, 22, 15), 66, proofResults);

      final Context context = rule.getActivity().getBaseContext();
      android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "d2360229bcbf4aeebc498d76f9f22a71");
      editor.apply();

      getResults(context);
      DataRequestMaker.saveResult(context, result, new AbstractDataManagerListener<Boolean>() {
         public void onResponse(Boolean response) {
            Log.d("SaveResultDataTest", "Chiamata saveResult() eseguita con successo");
            getResults(context);
         }
         public void onError(ServerError error) {
            Log.d("SaveResultDataTest", "Rilevato un errore in saveResult():");
            Log.d("SaveResultDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("SaveResultDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("SaveResultDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   void getResults(Context context) { //metodo di supporto, stampa tutti i risultati dell'utente
      final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN);
      DataRequestMaker.getResults(context, new AbstractDataManagerListener<PathResult[]>() {
         public void onResponse(PathResult[] response) {
            Log.d("SaveResultDataTest", "Chiamata getResults() eseguita con successo:");
            for(int i=0; i<response.length; i++) {
               Log.d("SaveResultDataTest", "   ID del percorso: " + response[i].pathID);
               Log.d("SaveResultDataTest", "   Nome del percorso: " + response[i].pathName);
               Log.d("SaveResultDataTest", "   Nome dell'edificio: " + response[i].buildingName);
               Log.d("SaveResultDataTest", "   Orario d'inizio: " + dateFormat.format(response[i].startTime.getTime()));
               Log.d("SaveResultDataTest", "   Orario di fine: " + dateFormat.format(response[i].endTime.getTime()));
               Log.d("SaveResultDataTest", "   Punteggio finale: " + response[i].totalScore);
               ArrayList<ProofResult> array = response[i].proofResults;
               for(int j=0; j<array.size(); j++) {
                  Log.d("SaveResultDataTest", "   Prova " + j + ":");
                  Log.d("SaveResultDataTest", "      ID della tappa: " + array.get(j).id);
                  Log.d("SaveResultDataTest", "      Orario d'inizio: " + dateFormat.format(array.get(j).startTime.getTime()));
                  Log.d("SaveResultDataTest", "      Orario di fine: " + dateFormat.format(array.get(j).endTime.getTime()));
                  Log.d("SaveResultDataTest", "      Punteggio della tappa: " + array.get(j).score);
               }
            }
         }
         public void onError(ServerError error) {
            Log.d("SaveResultDataTest", "Rilevato un errore in getResults():");
            Log.d("SaveResultDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("SaveResultDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("SaveResultDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
