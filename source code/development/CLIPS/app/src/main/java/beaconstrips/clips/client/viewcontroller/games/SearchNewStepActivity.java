/**
 * @file SearchNewStepActivity.java
 * @date 2016-07-18
 * @version 1.00
 * @author Viviana Alessio
 * Si occupa di gestire l'activity per la ricerca di un nuovo beacon
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import beaconstrips.clips.R;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

public class SearchNewStepActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_new_step);

        // prova statica
        setButton();
        /* TODO when proof beacon is found:
            TextView descriptionLabel = (TextView) findElementById(R.id.descriptionLabel);
            TextView hintLabel = (TextView) findElementById(R.id.hintLabel);
            Button startTestButton = Button findElementById(R.id.startTestButton);
            descriptionLabel.setVisibility(View.GONE);
            hintLabel.setVisibility(View.GONE);
            startTestButton.setVisibility(View.VISIBLE);
         */

        //TODO add all algorithms for proximity
    }

    void setButton() {
        final Button startTest = (Button) findViewById(R.id.startTestButton);
        if (startTest != null) {
            startTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MultipleChoiceQuizActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
