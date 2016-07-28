package beaconstrips.clips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import beaconstrips.clips.client.viewcontroller.authentication.AccountActivity;
import beaconstrips.clips.client.viewcontroller.authentication.LoginActivity;
import beaconstrips.clips.client.viewcontroller.authentication.RegistrationActivity;
import beaconstrips.clips.client.viewcontroller.building.BuildingActivity;
import beaconstrips.clips.client.viewcontroller.building.BuildingSearchActivity;
import beaconstrips.clips.client.viewcontroller.games.SearchNewStepActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.RankingActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.ResultActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.SavedResultsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SearchNewStepActivity.class);
        startActivity(intent);

    }
}
