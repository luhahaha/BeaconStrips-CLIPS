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

import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.CheckResult;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file CheckDataTest.java
 * @date 23/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU22 (Test di Unità 22). Verifica che il check dei dati digitati dall'utente sia effettuato correttamente.
 * Verranno proposte 3 versioni del test: la prima controllerà dei dati corretti, la seconda dei dati con formato errato, la terza dei dati già esistenti.
 * Ogni dato viene trattato da solo, quindi avere più dati errati non crea interferenze sul check.
 *
 *
 * Stampa attesa per il test "check": "Richiesta check() eseguita con successo:"
 *                                    "   Tipo: email"
 *                                    "      È valido: true"
 *                                    "      Perché: "
 *                                    "      Messaggio di debug: "
 *                                    "   Tipo: username"
 *                                    "      È valido: true"
 *                                    "      Perché: "
 *                                    "      Messaggio di debug: "
 *                                    "   Tipo: password"
 *                                    "      È valido: true"
 *                                    "      Perché: "
 *                                    "      Messaggio di debug: "
 * Stampa attesa per il test "checkWrongData": "Richiesta checkWrongData() con dati con formato errato eseguita con successo:"
 *                                    "   Tipo: email"
 *                                    "      È valido: false"
 *                                    "      Perché: invalid email"
 *                                    "      Messaggio di debug: invalid email"
 *                                    "   Tipo: username"
 *                                    "      È valido: true"
 *                                    "      Perché: "
 *                                    "      Messaggio di debug: "
 *                                    "   Tipo: password"
 *                                    "      È valido: false"
 *                                    "      Perché: invalid password"
 *                                    "      Messaggio di debug: invalid password"
 * Stampa attesa per il test "checkExistingData": "Richiesta checkExistingData() con dati già usati eseguita con successo:"
 *                                    "   Tipo: email"
 *                                    "      È valido: false"
 *                                    "      Perché: "
 *                                    "      Messaggio di debug: "
 *                                    "   Tipo: username"
 *                                    "      È valido: false"
 *                                    "      Perché: username is not available"
 *                                    "      Messaggio di debug: username is not available"
 *                                    "   Tipo: password"
 *                                    "      È valido: true"
 *                                    "      Perché: "
 *                                    "      Messaggio di debug: "
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CheckDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void check() {
      Context context = rule.getActivity().getBaseContext();
      LoginManager.sharedManager(context).check("prova33@gmail.com", "Prova33", "prova33", new AbstractDataManagerListener<CheckResult[]>() {
         public void onResponse(CheckResult[] response) {
            Log.d("CheckDataTest", "Richiesta check() eseguita con successo:");
            for(int i=0; i<3; i++) {
               Log.d("CheckDataTest", "   Tipo: " + response[i].type);
               Log.d("CheckDataTest", "      È valido: " + response[i].isValid);
               Log.d("CheckDataTest", "      Perché: " + response[i].reason);
               Log.d("CheckDataTest", "      Messaggio di debug: " + response[i].debugMessage);
            }
         }
         public void onError(ServerError error) {
            Log.d("CheckDataTest", "Rilevato un errore in check():");
            Log.d("CheckDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("CheckDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("CheckDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void checkWrongData() {
      Context context = rule.getActivity().getBaseContext();
      LoginManager.sharedManager(context).check("provagmail.com", "Prova33", "prova", new AbstractDataManagerListener<CheckResult[]>() {
         public void onResponse(CheckResult[] response) {
            Log.d("CheckDataTest", "Richiesta checkWrongData() con dati con formato errato eseguita con successo:");
            for(int i=0; i<3; i++) {
               Log.d("CheckDataTest", "   Tipo: " + response[i].type);
               Log.d("CheckDataTest", "      È valido: " + response[i].isValid);
               Log.d("CheckDataTest", "      Perché: " + response[i].reason);
               Log.d("CheckDataTest", "      Messaggio di debug: " + response[i].debugMessage);
            }
         }
         public void onError(ServerError error) {
            Log.d("CheckDataTest", "Rilevato un errore in checkWrongData():");
            Log.d("CheckDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("CheckDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("CheckDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void checkExistingData() {
      Context context = rule.getActivity().getBaseContext();
      LoginManager.sharedManager(context).check("prova@gmail.com", "Prova", "prova33", new AbstractDataManagerListener<CheckResult[]>() {
         public void onResponse(CheckResult[] response) {
            Log.d("CheckDataTest", "Richiesta checkExistingData() con dati già usati eseguita con successo:");
            for(int i=0; i<3; i++) {
               Log.d("CheckDataTest", "   Tipo: " + response[i].type);
               Log.d("CheckDataTest", "      È valido: " + response[i].isValid);
               Log.d("CheckDataTest", "      Perché: " + response[i].reason);
               Log.d("CheckDataTest", "      Messaggio di debug: " + response[i].debugMessage);
            }
         }
         public void onError(ServerError error) {
            Log.d("CheckDataTest", "Rilevato un errore in checkExistingData():");
            Log.d("CheckDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("CheckDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("CheckDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
