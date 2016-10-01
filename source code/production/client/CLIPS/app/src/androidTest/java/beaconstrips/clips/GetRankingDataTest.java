package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import beaconstrips.clips.client.data.Score;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file GetRankingDataTest.java
 * @date 01/09/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU26 (Test di Unità 26). Verifica che la richiesta dei risultati ottenuti dall'utente sia effettuata correttamente.
 *
 * Stampa attesa per il test "getRanking": "Chiamata getRanking() eseguita con successo:"
 *                                         "<array di score con formato: '<posizione>. <username>: <punteggio>'>"
 *                                         "<posizione>. Cenze: <punteggio>"
 *                                         "<array di score con formato: '<posizione>. <username>: <punteggio>'>"
 * Legenda: "<position>" indica la posizione dell'utente nella classifica, "<username>" lo username dell'utente che ha ottenuto quel punteggio e "<punteggio>" il risultato finale.
 * La chiamata dovrebbe restituire un array di punteggi, dove è atteso anche il punteggio dell'utente usato per la prova. La posizione e il punteggio non sono prevedibili perché possono variare quando questo o un altro utente completano quel percorso.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GetRankingDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void getRanking() {
      Context context = rule.getActivity().getBaseContext();
      android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "d2360229bcbf4aeebc498d76f9f22a71");
      editor.apply();
      DataRequestMaker.getRanking(context, 5, new AbstractDataManagerListener<Score[]>() {
         public void onResponse(Score[] response) {
            Log.d("GetRankingDataTest", "Chiamata getRanking() eseguita con successo:");
            for(int i=0; i<response.length; i++) {
               Log.d("GetRankingDataTest", response[i].position + ". " + response[i].username + ": " + response[i].score);
            }
         }
         public void onError(ServerError error) {
            Log.d("GetRankingDataTest", "Rilevato un errore in getRanking():");
            Log.d("GetRankingDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("GetRankingDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("GetRankingDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
