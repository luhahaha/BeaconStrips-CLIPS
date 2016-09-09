package beaconstrips.clips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import beaconstrips.clips.client.viewcontroller.authentication.AccountActivity;
import beaconstrips.clips.client.viewcontroller.authentication.LoginActivity;
import beaconstrips.clips.client.viewcontroller.authentication.RegistrationActivity;
import beaconstrips.clips.client.viewcontroller.building.BuildingActivity;
import beaconstrips.clips.client.viewcontroller.building.BuildingSearchActivity;
import beaconstrips.clips.client.viewcontroller.games.ProofResultActivity;
import beaconstrips.clips.client.viewcontroller.games.SearchNewStepActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.RankingActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.ResultActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.SavedResultsActivity;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;
import beaconstrips.clips.client.viewcontroller.utility.SplashActivity;

public class MainActivity extends MenuActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Intent intent = new Intent(this, SplashActivity.class);
      startActivity(intent);
      //setContentView(R.layout.activity_search_new_step);

   }

}