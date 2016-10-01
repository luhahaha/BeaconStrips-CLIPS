package beaconstrips.clips;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
 * @file NoServicesTest.java
 * @date 25/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU29 (Test di Unità 29). Verifica che il sistema sia solido quando effettua un'operazione che richiede un servizio non disponibile.
 * Verranno fatti 3 test: il primo verificherà il caso in cui non c'è una connessione ad Internet, e prevederà due chiamate, una in cui viene restituito un oggetto JSON e un'altra dove viene ricevuto un array JSON;
 * il secondo verificherà il caso in cui non è attivo il GPS e viene richiesto di trovare la posizione dell'utente; l'ultimo infine chiederà di cercare un beacon con il Bluetooth spento.
 * Ognuno di questi test avrà un esito positivo solo se il relativo servizio non è attivo, dato che non è possibile disattivarlo da codice verrà fatto un check che permetterà al test di andare a buon fine solo se effettivamente quel servizio non risulta attivo.
 *
 *
 * Stampa attesa per il test "noConnection": "Rilevato un errore in noConnection():"
 *                                           "Codice dell'errore: 1005"
 *                                           "Messaggio per l'utente: Errore di connessione"
 *                                           "Messaggio di debug: No Internet access, check your Internet connection"
 *                                           "Rilevato un errore in noConnection():"
 *                                           "Codice dell'errore: 1005"
 *                                           "Messaggio per l'utente: Errore di connessione"
 *                                           "Messaggio di debug: No Internet access, check your Internet connection"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class NoServicesTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void noConnection(){
      Context context = rule.getActivity().getBaseContext();
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo netInfo = cm.getActiveNetworkInfo();
      if(netInfo != null && netInfo.isConnectedOrConnecting()) {
         Log.d("NoServicesTest", "Connessione attiva. Impossibile eseguire il test.");
      } else {
         RequestMaker.getAppInfo(context, new AbstractUrlRequestListener() {
            public void onResponse(JSONObject response) {
               Log.d("NoServicesTest", "Chiamata noConnection() eseguita con successo");
            }
            public void onError(ServerError error) {
               Log.d("NoServicesTest", "Rilevato un errore in noConnection():");
               Log.d("NoServicesTest", "Codice dell'errore: " + error.errorCode);
               Log.d("NoServicesTest", "Messaggio per l'utente: " + error.userMessage);
               Log.d("NoServicesTest", "Messaggio di debug: " + error.debugMessage);
            }
         });
         RequestMaker.getResults(context, new AbstractUrlRequestListener() {
            public void onResponse(JSONObject response) {
               Log.d("NoServicesTest", "Chiamata noConnection() eseguita con successo");
            }
            public void onError(ServerError error) {
               Log.d("NoServicesTest", "Rilevato un errore in noConnection():");
               Log.d("NoServicesTest", "Codice dell'errore: " + error.errorCode);
               Log.d("NoServicesTest", "Messaggio per l'utente: " + error.userMessage);
               Log.d("NoServicesTest", "Messaggio di debug: " + error.debugMessage);
            }
         });
      }
   }

   @Test
   public void noGPS(){
      Context context = rule.getActivity().getBaseContext();
      final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
      if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
         Log.d("NoServicesTest", "Posizione attiva. Impossibile eseguire il test.");
      } else {
         PathProgressMaker.getCoordinates(context, new GPSListener() {
            public void onResponse(double latitude, double longitude) {
               Log.d("NoServicesTest", "Chiamata noGPS() eseguita con successo");
            }
            public void onError(ServerError error) {
               Log.d("NoServicesTest", "Rilevato un errore in noGPS():");
               Log.d("NoServicesTest", "Codice dell'errore: " + error.errorCode);
               Log.d("NoServicesTest", "Messaggio per l'utente: " + error.userMessage);
               Log.d("NoServicesTest", "Messaggio di debug: " + error.debugMessage);
            }
         });
      }
   }
}
