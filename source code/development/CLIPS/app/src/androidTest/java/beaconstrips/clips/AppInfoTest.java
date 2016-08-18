package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.json.JSONObject;

import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file AppInfoTest.java
 * @date 18/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU1 (Test di Unità 1). Verifica che la richiesta delle informazioni sell'applicazione siano restituite correttamente.
 * Dato che la chiamata non richiede parametri non esistono errori, ritornati dal server, caratteristici di questo tipo di richiesta; di conseguenza non ci sono test relativi agli errori restituiti dal server.
 *
 *
 * Stampa attesa per il test "appInfo": "Chiamata eseguita con successo"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class AppInfoTest{
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void appInfo() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.getAppInfo(context, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("AppInfoTest", "Chiamata eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("AppInfoTest", "Rilevato un errore:");
            Log.d("AppInfoTest", "Codice dell'errore: " + error.errorCode);
            Log.d("AppInfoTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("AppInfoTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
