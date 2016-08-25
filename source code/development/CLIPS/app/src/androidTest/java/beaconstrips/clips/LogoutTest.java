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

import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file LogoutTest.java
 * @date 25/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU8 (Test di Unità 8). Verifica che la richiesta al server di fare il logout dell'utente sia effettuata correttamente.
 * Nota: affinché il test vada l'utente dev'essere loggato, altrimenti fallirà.
 *
 *
 * Stampa attesa per il test "logout": "Chiamata logout() eseguita con successo"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LogoutTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void logout() {
      Context context = rule.getActivity().getBaseContext();
      if(LoginManager.sharedManager(context).isLogged()) {
         RequestMaker.logout(context, new AbstractUrlRequestListener() {
            public void onResponse(JSONObject response) {
               Log.d("LogoutTest", "Chiamata logout() eseguita con successo");
            }

            public void onError(ServerError error) {
               Log.d("LogoutTest", "Rilevato un errore in logout():");
               Log.d("LogoutTest", "Codice dell'errore: " + error.errorCode);
               Log.d("LogoutTest", "Messaggio per l'utente: " + error.userMessage);
               Log.d("LogoutTest", "Messaggio di debug: " + error.debugMessage);
            }
         });
      } else {
         Log.d("LogoutTest", "L'utente non è loggato. Impossibile eseguire il test.");
      }
   }
}
