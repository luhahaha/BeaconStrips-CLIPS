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
 * classe che contiene il TU (Test di Unità ). Verifica che la richiesta ad una pagina non esistente venga gestita correttamente, un caso possibile ad esempio se il server cambia indirizzo e il client non è ancora stato aggiornato.
 * Inoltre verifica che venga gestito correttamente il caso in cui l'applicazione perde l'accesso ad Internet, ad esempio perché il dispositivo si trova in un'area poco coperta dal proprio operatore telefonico.
 * Per il primo test verrà utilizzata come chiamata AppInfo, perché è la più semplice da implementare e una di quelle che genera meno situazioni diverse possibili.
 *
 *
 * Stampa attesa per il test "wrongAddress": "Rilevato un errore in wrongAddress():"
 *                                           "Codice dell'errore: 1000"
 *                                           "Messaggio per l'utente: "
 *                                           "Messaggio di debug: Error server isn't a JSON, the error code is 404"
 * Nota: dato che l'errore 404 è un html e non un JSON viene lanciata un'eccezione quando si cerca di parsare la risposta, di conseguenza è previsto che venga creato un errore ad hoc per informare in modo sicuro l'applicazione.
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
            Log.d("AppInfoTest", "Chiamata wrongAddress() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("AppInfoTest", "Rilevato un errore in wrongAddress():");
            Log.d("AppInfoTest", "Codice dell'errore: " + error.errorCode);
            Log.d("AppInfoTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("AppInfoTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
      field.set(address, "http://52.58.6.246:1234/"); //viene ripristinato l'indirizzo originale per sicurezza, così se ci sono altri test da eseguire non vengono compromessi. Il cambio viene effettuato subito dopo aver effettuato la richiesta perché l'indirizzo errato non serve più poi
   }
}
