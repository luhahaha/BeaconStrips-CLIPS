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
 * @file BuildingsTest.java
 * @date 19/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU5 (Test di Unità 5). Verifica che la richiesta al server dei dati degli edifici più vicini siano restituiti correttamente.
 * Viene dato per scontato che i dati ci siano tutti, quindi non dovrebbero verificarsi errori.
 * I test previsti quindi sono due, uno che richiede un certo numero massimo di edifici e uno che richiede quelli situati entro una certa distanza.
 *
 *
 * Stampa attesa per il test "buildingsByDistance": "Chiamata buildingsByDistance() eseguita con successo"
 * Stampa attesa per il test "buildingsByNumber": "Chiamata buildingsByNumber() eseguita con successo"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class BuildingsTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void buildingsByDistance() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.getBuildings(context, 45, 5, 5, true, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("BuildingsTest", "Chiamata buildingsByDistance() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("BuildingsTest", "Rilevato un errore in buildingsByDistance():");
            Log.d("BuildingsTest", "Codice dell'errore: " + error.errorCode);
            Log.d("BuildingsTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("BuildingsTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void buildingsByNumber() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.getBuildings(context, 45, 5, 1, false, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("BuildingsTest", "Chiamata buildingsByNumber() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("BuildingsTest", "Rilevato un errore in buildingsByNumber():");
            Log.d("BuildingsTest", "Codice dell'errore: " + error.errorCode);
            Log.d("BuildingsTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("BuildingsTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
