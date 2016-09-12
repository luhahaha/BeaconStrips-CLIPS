/**
 * @file TrueFalseQuizActivity.java
 * @date 2016-07-18
 * @version 1.40
 * @author Viviana Alessio
 * Gestisce l'activity per i quiz vero/falso
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.TrueFalseTest;
import beaconstrips.clips.client.data.TrueFalseTextQuiz;

public class TrueFalseQuizActivity extends AppCompatActivity {

    private final String TAG = "TrueFalseQuizActivity";
    private Intent i;
    private TrueFalseTest test;
    private TrueFalseTextQuiz answers;
    int correctAnswers;
    boolean answer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false_quiz);
        i = getIntent();
        correctAnswers = i.getIntExtra("correctAnswers", 0);
        test = (TrueFalseTest) i.getSerializableExtra("test");
        answers = test.questions.remove(0);

        setQuiz();
        setButton();
    }

    private void setQuiz() {
        ((TextView) findViewById(R.id.questionLabel)).setText(answers.instructions);

        i.setClass(getApplicationContext(), ProofResultActivity.class);
        Button trueButton = (Button) findViewById(R.id.trueButton);
        Button falseButton = (Button) findViewById(R.id.falseButton);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answers.response) {
                    i.putExtra("correctAnswers", ++correctAnswers);
                    i.putExtra("answer", true);
                }
                else {
                    i.putExtra("answer", false);
                }
                startActivity(i);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answers.response) {
                    i.putExtra("correctAnswers", ++correctAnswers);
                    i.putExtra("answer", true);

                }
                else {
                    i.putExtra("answer", false);
                }
                startActivity(i);
            }
        });
    }

    public void setButton() {
        Button showHint = (Button) findViewById(R.id.showHint);
        showHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView hintLabel = (TextView) findViewById(R.id.hintLabel);
                hintLabel.setText(answers.helpText);
                hintLabel.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
