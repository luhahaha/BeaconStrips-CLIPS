/**
 * @file ProofResultActivity.java
 * @date 2016-07-17
 * @version 1.40
 * @author Viviana Alessio
 * Si occupa dell'activity di visualizzazione del risultato della prova effettuata
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.GregorianCalendar;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.Test;
import beaconstrips.clips.client.data.TrueFalseTest;
import beaconstrips.clips.client.pathprogress.PathProgressController;
import beaconstrips.clips.client.viewcontroller.savedresults.ResultActivity;

public class ProofResultActivity extends AppCompatActivity {

    private Intent i;
    private PathProgressController pathProgress;
    private String TAG = "ProofResultActivity";
    boolean finished;
    private int correctAnswers;
    private Test test;
    private GregorianCalendar startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof_result);


        setButton();
        i = getIntent();

        boolean showText = i.getBooleanExtra("answer", true);
        correctAnswers = i.getIntExtra("correctAnswers", 0);



        if(!showText) {
            ((TextView) findViewById(R.id.resultMessage)).setText("Risposta sbagliata!");
        }
        else {
            ((TextView) findViewById(R.id.resultMessage)).setText("Risposta corretta!");
        }


        test = (Test) i.getSerializableExtra("test");

        startTime = (GregorianCalendar) i.getSerializableExtra("startTime");
        Bundle bundle = i.getExtras();

        pathProgress = (PathProgressController) bundle.getSerializable("pathProgress");

    }

    private void setButton() {

        Button next = (Button) findViewById(R.id.searchNextBeacon);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int testLeft = 0;
                if (test instanceof MultipleChoiceTest) {
                    testLeft = ((MultipleChoiceTest) test).questions.size();
                    i.setClass(getApplicationContext(), MultipleChoiceQuizActivity.class);
                }
                if (test instanceof TrueFalseTest) {
                    testLeft = ((TrueFalseTest) test).questions.size();
                    i.setClass(getApplicationContext(), TrueFalseQuizActivity.class);
                }
                if (testLeft > 0) {
                    startActivity(i);
                }
                else {
                    int totalQuestions = i.getIntExtra("totalQuestions", 0);
                    finished = pathProgress.savedResult(startTime, new GregorianCalendar(), correctAnswers, totalQuestions); //if true ho finito il percorso
                    if (!finished) {
                        i.setClass(getApplicationContext(), SearchNewStepActivity.class);
                        i.putExtra("correctAnswers", 0);
                    }
                    else {
                        i.putExtra("totalScore", pathProgress.getTotalScore());
                        i.setClass(getApplicationContext(), ResultActivity.class);
                    }
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
