package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import beaconstrips.clips.client.data.AppInfo;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file AppInfoTest.java
 * @date 18/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU14 (Test di Unità 14). Verifica che la ricerca delle informazioni sell'applicazione sia effettuata correttamente.
 *
 * Stampa attesa per il test "appInfo": "Chiamata appInfo() eseguita con successo:"
 *                                      "   Descrizione: Descrizione dell'applicazione"
 *                                      "   Email degli sviluppatori: beaconstrips.swe@gmail.com"
 *                                      "   Sito web: 52.58.6.246"
 *                                      "   UUID dei beacon: asdfhjlk-hjkl-fdas-jklh-fdas-fjdkalhd"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class AppInfoDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void appInfo() {
      Context context = rule.getActivity().getBaseContext();
      DataRequestMaker.getAppInfo(context, new AbstractDataManagerListener<AppInfo>() {
         public void onResponse(AppInfo response) {
            Log.d("AppInfoDataTest", "Richiesta appInfo() eseguita con successo:");
            Log.d("AppInfoDataTest", "   Descrizione: " + response.description);
            Log.d("AppInfoDataTest", "   Email degli sviluppatori: " + response.supportEmail);
            Log.d("AppInfoDataTest", "   Sito web: " + response.website);
            Log.d("AppInfoDataTest", "   UUID dei beacon: " + response.UUID);
         }
         public void onError(ServerError error) {
            Log.d("AppInfoDataTest", "Rilevato un errore in appInfo():");
            Log.d("AppInfoDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("AppInfoDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("AppInfoDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
