package beaconstrips.clips;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
 * @file GetProfileDataTest.java
 * @date 26/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU11 (Test di Unità 11). Verifica che la richiesta al server di ottenere i dati del profilo dell'utente sia effettuata correttamente.
 * Nota: affinché il test vada a buon fine il token salvato in locale deve essere valido, altrimenti fallirà.
 *
 *
 * Stampa attesa per il test "getProfileData": "Chiamata getProfileData() eseguita con successo"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GetProfileDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void getProfileData() {
      Context context = rule.getActivity().getBaseContext();
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
      SharedPreferences.Editor editor = preferences.edit();
      editor.putString("token", "dd664cb2ee2e42da90733dc8c8b2a731");
      editor.apply();
      RequestMaker.getProfileData(context, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("GetProfileDataTest", "Chiamata getProfileData() eseguita con successo");
         }

         public void onError(ServerError error) {
            Log.d("GetProfileDataTest", "Rilevato un errore in getProfileData():");
            Log.d("GetProfileDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("GetProfileDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("GetProfileDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
