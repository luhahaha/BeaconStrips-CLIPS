package beaconstrips.clips;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.urlrequest.URLDataConstants;

/**
 * @file RequestGenericTest.java
 * @date 20/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU28 (Test di Unità 28). Verifica che la richiesta ad una pagina non esistente venga gestita correttamente, un caso possibile ad esempio se il server cambia indirizzo e il client non è ancora stato aggiornato.
 * Per il test verrà utilizzata come chiamata AppInfo, perché è la più semplice da implementare e una di quelle che genera meno situazioni diverse possibili.
 * Verifica inoltre che l'invio di un token errato ritorni un errore gestito correttamente, la chiamata usata sarà getResults, una delle chiamate più semplici che richiede l'utilizzo del token. È possibile ottenere questo errore quando, ad esempio, il token è scaduto e quindi è stato eliminato dal database del server.
 *
 *
 * Stampa attesa per il test "wrongAddress": "Rilevato un errore in wrongAddress():"
 *                                           "Codice dell'errore: 1000"
 *                                           "Messaggio per l'utente: "
 *                                           "Messaggio di debug: Error server isn't a JSON, the error code is 404"
 * Nota: dato che l'errore 404 è un html e non un JSON viene lanciata un'eccezione quando si cerca di fare il parsing della risposta, di conseguenza è previsto che venga creato un errore ad hoc per informare in modo sicuro l'applicazione.
 * Stampa attesa per il test "wrongToken": "Rilevato un errore in wrongToken():"
 *                                         "Codice dell'errore: 401"
 *                                         "Messaggio per l'utente: "
 *                                         "Messaggio di debug: "
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RequestGenericTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void wrongAddress() throws Exception{ //prelevo il campo "baseURL" da URLDataConstants, lo rendo accessibile e lo modifico per il test
      URLDataConstants address = new URLDataConstants();
      Field field = address.getClass().getDeclaredField("baseURL");
      field.setAccessible(true);
      field.set(address, "http://52.58.6.246/"); //è stata tolta la porta d'accesso, in questo modo la chiamata cerca di accedere al server tramite la porta 80 standard, ma non riuscirà mai a trovare le pagine cercate

      Context context = rule.getActivity().getBaseContext();
      RequestMaker.getAppInfo(context, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("wrongAddress", "Chiamata wrongAddress() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("wrongAddress", "Rilevato un errore in wrongAddress():");
            Log.d("wrongAddress", "Codice dell'errore: " + error.errorCode);
            Log.d("wrongAddress", "Messaggio per l'utente: " + error.userMessage);
            Log.d("wrongAddress", "Messaggio di debug: " + error.debugMessage);
         }
      });
      field.set(address, "http://52.58.6.246:1234/"); //viene ripristinato l'indirizzo originale per sicurezza, così se ci sono altri test da eseguire non vengono compromessi. Il cambio viene effettuato subito dopo aver effettuato la richiesta perché l'indirizzo errato non serve più poi
   }

   @Test
   public void wrongToken() throws Exception{
      Context context = rule.getActivity().getBaseContext();
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
      SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "abbab9");
      editor.apply();
      RequestMaker.getResults(context, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("wrongToken", "Chiamata wrongToken() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("wrongToken", "Rilevato un errore in wrongToken():");
            Log.d("wrongToken", "Codice dell'errore: " + error.errorCode);
            Log.d("wrongToken", "Messaggio per l'utente: " + error.userMessage);
            Log.d("wrongToken", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
