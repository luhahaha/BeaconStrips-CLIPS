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
import beaconstrips.clips.client.data.MultipleChoiceTextQuiz;
import beaconstrips.clips.client.data.Path;

public class MultipleChoiceQuizActivity extends AppCompatActivity {

    private Path path;
    private int pathIndex;
    private final String TAG = "MultipleChoiceQuizAct";
    private MultipleChoiceTextQuiz answers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_quiz);
        //TODO insert from here choices with correct answer with list?
        Intent i = getIntent();
        MultipleChoiceTest test = (MultipleChoiceTest) i.getSerializableExtra("test");
        answers = test.questions.get(0);

        Log.i(TAG, "Size of" + test.questions.size());
        Log.i(TAG, "Correct answer " + answers.trueResponse);


        setQuiz();

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

    private void setQuiz() {

        ((Button) findViewById(R.id.choice1)).setText(answers.trueResponse);
        ((Button) findViewById(R.id.choice4)).setText(answers.falseResponse[0]);
        ((Button) findViewById(R.id.choice2)).setText(answers.falseResponse[1]);
        ((Button) findViewById(R.id.choice3)).setText(answers.falseResponse[2]);
    }
}
