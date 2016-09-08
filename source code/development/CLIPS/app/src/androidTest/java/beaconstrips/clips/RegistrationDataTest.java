package beaconstrips.clips;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import beaconstrips.clips.client.data.LoggedUser;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file RegistrationDataTest.java
 * @date 24/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU19 (Test di Unità 19). Verifica che la richiesta di effettuare la registrazione del profilo dell'utente sia effettuata correttamente.
 * Per verificare che i dati restituiti dalla chiamata siano corretti verranno stampati da due fonti diverse: la prima è il file dove vengono salvati questi dati, in modo da recuperarli ad ogni avvio dell'applicazione; la seconda è l'oggetto LoggedUser in cui vengono salvati i dati per poterli recuperare rapidamente durante l'esecuzione dell'applicazione.
 *
 * Stampa attesa per il test "registration": "Chiamata registration() eseguita con successo:"
 *                                           "   Token del file: <token>"
 *                                           "   Token di loggedUser: <token>"
 *                                           "   Email del file: prova34@gmail.com"
 *                                           "   Email di loggedUser: prova34@gmail.com"
 *                                           "   Username del file: Prova34"
 *                                           "   Username di loggedUser: Prova34"
 * Nota: il token viene generato e fornito dal server, quindi non è possibile prevedere quale sarà il suo valore; di conseguenza il valore atteso è sostituito dall'espressione "<token>". L'importante è che il valore salvato nel file coincida con quello di loggedUser.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RegistrationDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void registration() {
      final Context context = rule.getActivity().getBaseContext();
      LoginManager.sharedManager(context).registration("prova34@gmail.com", "Prova34", "prova34", new AbstractDataManagerListener<Boolean>() {
         public void onResponse(Boolean response) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            LoggedUser loggedUser = LoginManager.sharedManager(context).getLoggedUser();
            Log.d("RegistrationDataTest", "Chiamata registration() eseguita con successo:");
            Log.d("RegistrationDataTest", "   Token del file: " + preferences.getString("token", ""));
            Log.d("RegistrationDataTest", "   Token di loggedUser: " + loggedUser.token);
            Log.d("RegistrationDataTest", "   Email del file: " + preferences.getString("email", ""));
            Log.d("RegistrationDataTest", "   Email di loggedUser: " + loggedUser.email);
            Log.d("RegistrationDataTest", "   Username del file: " + preferences.getString("username", ""));
            Log.d("RegistrationDataTest", "   Username di loggedUser: " + loggedUser.username);
         }
         public void onError(ServerError error) {
            Log.d("RegistrationDataTest", "Rilevato un errore in registration():");
            Log.d("RegistrationDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("RegistrationDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("RegistrationDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
