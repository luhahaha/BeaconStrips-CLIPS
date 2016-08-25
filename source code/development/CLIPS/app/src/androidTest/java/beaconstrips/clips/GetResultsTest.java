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
 * @file GetResultsTest.java
 * @date 25/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU2 (Test di Unità 2). Verifica che la richiesta dei risultati ottenuti dall'utente sia effettuata correttamente.
 *
 * Stampa attesa per il test "getResults": "Chiamata getResults() eseguita con successo"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GetResultsTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void getResults() {
      Context context = rule.getActivity().getBaseContext();
      android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
      android.content.SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "3ebad59a194d");
      editor.apply();
      RequestMaker.getResults(context, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("GetResultsTest", "Chiamata getResults() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("GetResultsTest", "Rilevato un errore in getResults():");
            Log.d("GetResultsTest", "Codice dell'errore: " + error.errorCode);
            Log.d("GetResultsTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("RGetResultsTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
