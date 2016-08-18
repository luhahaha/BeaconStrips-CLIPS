package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.json.JSONObject;

import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * Created by andrea on 18/08/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class AppInfoTest{
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void appInfo() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.getAppInfo(context, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            System.out.println("Chiamata eseguita con successo");
         }
         public void onError(ServerError error) {
            System.out.println("Rilevato un errore:");
            System.out.println("Codice dell'errore: " + error.errorCode);
            System.out.println("Messaggio per l'utente: " + error.userMessage);
            System.out.println("Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
