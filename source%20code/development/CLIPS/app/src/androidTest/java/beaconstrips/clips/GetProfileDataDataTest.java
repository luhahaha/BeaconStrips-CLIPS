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
 * @file GetProfileDataDataTest.java
 * @date 26/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU24 (Test di Unità 24). Verifica che la richiesta di ottenere i dati del profilo dell'utente sia effettuata correttamente.
 * Nota: affinché il test vada a buon fine il token salvato in locale deve essere valido, altrimenti fallirà.
 *
 *
 * Stampa attesa per il test "getProfileData": "Email salvata nel file: Email non valida"
 *                                             "Email salvata in loggedUser: Email non valida"
 *                                             "Username salvato nel file: Username non valido"
 *                                             "Username salvato in loggedUser: Username non valido"
 *                                             "Chiamata getProfileData() eseguita con successo"
 *                                             "   Email salvata nel file: <email>"
 *                                             "   Email salvata in loggedUser: <email>"
 *                                             "   Username salvato nel file: <username>"
 *                                             "   Username salvato in loggedUser: <username>"
 * Nota: "<email>" e "<username>" indicano che al loro posto dovrebbe essere stampato l'email o lo username dei dati resituiti:
 * tali valori variano a seconda del token usati, le uniche condizioni che devono verificarsi affinché il test vada a buon fine sono i dati salvati in locale uguali a quelli dei loggedUser e diversi da quelli stampati prima di effettuare la chiamata.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GetProfileDataDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void getProfileData() {
      final Context context = rule.getActivity().getBaseContext();
      final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
      SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "dd664cb2ee2e42da90733dc8c8b2a731");
      editor.putString("email", "Email non valida");
      editor.putString("username", "Username non valido");
      editor.apply();
      LoggedUser loggedUser = LoginManager.sharedManager(context).getLoggedUser();
      Log.d("GetProfileDatadataTest", "Email salvata nel file: " + preferences.getString("email", ""));
      Log.d("GetProfileDatadataTest", "Email salvata in loggedUser: " + loggedUser.email);
      Log.d("GetProfileDatadataTest", "Username salvato nel file: " + preferences.getString("username", ""));
      Log.d("GetProfileDatadataTest", "Username salvato in loggedUser: " + loggedUser.username);
      if(LoginManager.sharedManager(context).isLogged()) {
         LoginManager.sharedManager(context).getProfileData(new AbstractDataManagerListener<Boolean>() {
            public void onResponse(Boolean response) {
               final LoggedUser loggedUser = LoginManager.sharedManager(context).getLoggedUser();
               Log.d("GetProfileDatadataTest", "Chiamata getProfileData() eseguita con successo");
               Log.d("GetProfileDatadataTest", "   Email salvata nel file: " + preferences.getString("email", ""));
               Log.d("GetProfileDatadataTest", "   Email salvata in loggedUser: " + loggedUser.email);
               Log.d("GetProfileDatadataTest", "   Username salvato nel file: " + preferences.getString("username", ""));
               Log.d("GetProfileDatadataTest", "   Username salvato in loggedUser: " + loggedUser.username);
            }

            public void onError(ServerError error) {
               Log.d("GetProfileDataDataTest", "Rilevato un errore in getProfileData():");
               Log.d("GetProfileDataDataTest", "Codice dell'errore: " + error.errorCode);
               Log.d("GetProfileDataDataTest", "Messaggio per l'utente: " + error.userMessage);
               Log.d("GetProfileDataDataTest", "Messaggio di debug: " + error.debugMessage);
            }
         });
      } else {
         Log.d("GetProfileDataTest", "L'utente non è loggato. Impossibile eseguire il test.");
      }
   }
}
