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
 * @file GetRankingTest.java
 * @date 28/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU12 (Test di Unità 12). Verifica che la richiesta dei risultati ottenuti dall'utente sia effettuata correttamente.
 *
 * Stampa attesa per il test "getRanking": "Chiamata getRanking() eseguita con successo"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GetRankingTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void getRanking() {
      Context context = rule.getActivity().getBaseContext();
      android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "cf9ad04f81bd467a817b1da8f18ba858");
      editor.apply();
      RequestMaker.getRanking(context, 1, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("GetRankingTest", "Chiamata getRanking() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("GetRankingTest", "Rilevato un errore in getRanking():");
            Log.d("GetRankingTest", "Codice dell'errore: " + error.errorCode);
            Log.d("GetRankingTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("GetRankingTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
