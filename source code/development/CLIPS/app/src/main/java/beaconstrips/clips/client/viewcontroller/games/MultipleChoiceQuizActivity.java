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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.Path;

public class MultipleChoiceQuizActivity extends AppCompatActivity {

    private Path path;
    private int pathIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_quiz);
        //TODO insert from here choices with correct answer with list?
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        pathIndex = i.getIntExtra("pathIndex", 0);

        if (bundle != null) {
            path = (Path) bundle.getSerializable("Path");
            Log.i("Path", "Not null");
        }
        else
            Log.i("Path", "null");

        setQuiz(path, pathIndex);

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

    private void setQuiz(Path path, int pathIndex) {
        MultipleChoiceTest test = (MultipleChoiceTest) path.steps.get(pathIndex).proof.test;

        ((Button) findViewById(R.id.choice1)).setText(test.questions.get(0).trueResponse);
        ((Button) findViewById(R.id.choice2)).setText(test.questions.get(0).falseResponse[1]);
        ((Button) findViewById(R.id.choice3)).setText(test.questions.get(0).falseResponse[2]);
        ((Button) findViewById(R.id.choice4)).setText(test.questions.get(0).falseResponse[3]);
    }
}
