/**
 * @file MultipleChoiceQuizActivity.java
 * @date 2016-07-17
 * @version 1.00
 * @author Viviana Alessio
 * Gestisce l'activity per i quiz a risposta multipla
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import beaconstrips.clips.R;

public class MultipleChoiceQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_quiz);
        //TODO insert from here choices with correct answer with list?

        final Button answer = (Button) findViewById(R.id.choice3);
        if (answer != null) {
            answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ProofResultActivity.class);
                    startActivity(i);
                }
            });
        }

    }
}
