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
import java.util.Locale;

import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file GetResultsDataTest.java
 * @date 26/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU14 (Test di Unità 14). Verifica che la richiesta dei risultati ottenuti dall'utente sia effettuata correttamente.
 *
 * Stampa attesa per il test "getResults": "Chiamata getResults() eseguita con successo:"
 *                                         "<altri risultati>"
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
 *                                         "<altri risultati>"
 * Legenda: "<altri risultati>" indica che prima o dopo possono esserci altri risultati che rispettano la stessa struttura dell'unico risultato definito sopra.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GetResultsDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void getResults() {
      Context context = rule.getActivity().getBaseContext();
      android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "cf9ad04f81bd467a817b1da8f18ba858");
      editor.apply();
      final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN);
      DataRequestMaker.getResults(context, new AbstractDataManagerListener<PathResult[]>() {
         public void onResponse(PathResult[] response) {
            Log.d("GetResultsDataTest", "Chiamata getResults() eseguita con successo:");
            Log.d("GetResultsDataTest", "Risultato di prova: " + response[2].totalScore);
            for(int i=0; i<response.length; i++) {
               Log.d("GetResultsDataTest", "   ID del percorso: " + response[i].pathID);
               Log.d("GetResultsDataTest", "   Nome del percorso: " + response[i].pathName);
               Log.d("GetResultsDataTest", "   Nome dell'edificio: " + response[i].buildingName);
               Log.d("GetResultsDataTest", "   Orario d'inizio: " + dateFormat.format(response[i].startTime.getTime()));
               Log.d("GetResultsDataTest", "   Orario di fine: " + dateFormat.format(response[i].endTime.getTime()));
               Log.d("GetResultsDataTest", "   Punteggio finale: " + response[i].totalScore);
               ArrayList<ProofResult> array = response[i].proofResults;
               for(int j=0; j<array.size(); j++) {
                  Log.d("GetResultsDataTest", "   Prova " + j +":");
                  Log.d("GetResultsDataTest", "      ID della tappa: " + array.get(j).id);
                  Log.d("GetResultsDataTest", "      Orario d'inizio: " + dateFormat.format(array.get(j).startTime.getTime()));
                  Log.d("GetResultsDataTest", "      Orario di fine: " + dateFormat.format(array.get(j).endTime.getTime()));
                  Log.d("GetResultsDataTest", "      Punteggio della tappa: " + array.get(j).score);
               }
            }
         }
         public void onError(ServerError error) {
            Log.d("GetResultsDataTest", "Rilevato un errore in getResults():");
            Log.d("GetResultsDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("GetResultsDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("GetResultsDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
