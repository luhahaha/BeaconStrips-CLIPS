/**
 * @file SplashActivity.java
 * @date 2016-07-22
 * @version 1.10
 * @author Matteo Franco
 *
 * Rappresenta uno splash screen da mostrare all'avvio
 */

package beaconstrips.clips.client.viewcontroller.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import beaconstrips.clips.R;
import beaconstrips.clips.client.viewcontroller.building.BuildingSearchActivity;

public class SplashActivity extends Activity {

   private final int SPLASH_DISPLAY_LENGTH = 1000;

   @Override
   public void onCreate(Bundle icicle) {
      super.onCreate(icicle);
      setContentView(R.layout.activity_splash);

      new Handler().postDelayed(new Runnable(){
         @Override
         public void run() {
            Intent mainIntent = new Intent(SplashActivity.this, BuildingSearchActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
         }
      }, SPLASH_DISPLAY_LENGTH);
   }
}