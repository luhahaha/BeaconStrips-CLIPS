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

import beaconstrips.clips.client.data.LoggedUser;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file LogoutDataTest.java
 * @date 25/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU20 (Test di Unità 20). Verifica che la richiesta di fare il logout dell'utente sia effettuata correttamente.
 * Per verificare che ci sia l'aggiornamento del token, e quindi la sua eliminazione a seguito della richiesta, verrà stampato prima e dopo la chiamata leggendolo sia dal file dove è salvato sia dalla classe LoggedUser in cui è contenuto.
 * Nota: affinché il test vada a buon fine l'utente dev'essere loggato, altrimenti fallirà.
 *
 *
 * Stampa attesa per il test "logout": "Token salvato nel file: <token>"
 *                                     "Token salvato in loggedUser: <token>"
 *                                     "Chiamata logout() eseguita con successo"
 *                                     "Token salvato nel file: "
 *                                     "Token salvato in loggedUser: null"
 * Nota: Il token cambia è generato automaticamente dal server e quindi cambia ad ogni esecuzione, per questo è stato sostituito dall'espressione "<token>": l'importante è che il token del file e quello di loggedUser siano uguali.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LogoutDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void logout() {
      final Context context = rule.getActivity().getBaseContext();
      if(LoginManager.sharedManager(context).isLogged()) {
         LoggedUser loggedUser = LoginManager.sharedManager(context).getLoggedUser();
         Log.d("LogoutDataTest", "Token salvato nel file: " + LoginManager.sharedManager(context).getToken());
         Log.d("LogoutDataTest", "Token salvato in loggedUser: " + loggedUser.token);
         LoginManager.sharedManager(context).logout(new AbstractDataManagerListener<Boolean>() {
            public void onResponse(Boolean response) {
               LoggedUser loggedUserFinale = LoginManager.sharedManager(context).getLoggedUser();
               Log.d("LogoutDataTest", "Chiamata logout() eseguita con successo:");
               Log.d("LogoutDataTest", "Token salvato nel file: " + LoginManager.sharedManager(context).getToken());
               Log.d("LogoutDataTest", "Token salvato in loggedUser: " + loggedUserFinale);
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
