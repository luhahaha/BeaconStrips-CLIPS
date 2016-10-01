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

import beaconstrips.clips.client.pathprogress.GPSListener;
import beaconstrips.clips.client.pathprogress.PathProgressMaker;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file PositionTest.java
 * @date 25/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU37 (Test di Unità 37). Verifica che la richiesta di ottenre la posizione dell'utente tramite il GPS venga effettuata correttamente.
 *
 *
 * Stampa attesa per il test "getPosition": "Chiamata getPosition() eseguita con successo"
 *                                          "   Latitude: <latitudine>"
 *                                          "   Longitude: <longitude>"
 * Legenda: ovviamente i termini tra "<>" variano a seconda della zona dove viene eseguito il test.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class PositionTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void getPosition() {
      Context context = rule.getActivity().getBaseContext();
      PathProgressMaker.getCoordinates(context, new GPSListener() {
         public void onResponse(double latitude, double longitude) {
            Log.d("PositionTest", "Chiamata getPosition() eseguita con successo:");
            Log.d("PositionTest", "   Latitudine: " + latitude);
            Log.d("PositionTest", "   Longitude: " + longitude);
         }
         public void onError(ServerError error) {
            Log.d("PositionTest", "Rilevato un errore in getPosition():");
            Log.d("PositionTest", "Codice dell'errore: " + error.errorCode);
            Log.d("PositionTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("PositionTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
