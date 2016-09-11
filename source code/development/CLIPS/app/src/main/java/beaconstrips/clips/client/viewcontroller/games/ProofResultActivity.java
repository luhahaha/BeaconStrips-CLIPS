/**
 * @file ProofResultActivity.java
 * @date 2016-07-17
 * @version 1.00
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

import com.google.android.gms.vision.text.Text;

import java.util.GregorianCalendar;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.Test;
import beaconstrips.clips.client.data.TrueFalseTest;
import beaconstrips.clips.client.pathprogress.PathProgressController;
import beaconstrips.clips.client.viewcontroller.savedresults.ResultActivity;

public class ProofResultActivity extends AppCompatActivity {

    private int stepIndex;
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
        //TODO aggiungere risposte totali
        //totalAnswers = i.getIntExtra("totalAnswers", 0);

        //Log.i(TAG, "Answer is " + showText);

        if(!showText) {
            ((TextView) findViewById(R.id.resultMessage)).setText("Risposta sbagliata!");
        }
        else {
            ((TextView) findViewById(R.id.resultMessage)).setText("Risposta corretta!");

        }

        //quizLeft = i.getIntExtra("quizLeft", 0);

        test = (Test) i.getSerializableExtra("test");

        if(test == null) {
            //Log.i(TAG, "Test is null");
        }

        startTime = (GregorianCalendar) i.getSerializableExtra("startTime");
        Bundle bundle = i.getExtras();
        //Log.i(TAG, "Start time" + startTime);

        pathProgress = (PathProgressController) bundle.getSerializable("pathProgress");
        //if(pathProgress != null)
        //stepIndex = i.getIntExtra("stepIndex", 0);
    }

    private void setButton() {

        Button next = (Button) findViewById(R.id.searchNextBeacon);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(stepIndex == steps.size()) {
                int testLeft = 0;
                if (test instanceof MultipleChoiceTest) {
                    testLeft = ((MultipleChoiceTest) test).questions.size();
                    //Log.i(TAG, "Test left " + testLeft);
                    i.setClass(getApplicationContext(), MultipleChoiceQuizActivity.class);
                }
                if (test instanceof TrueFalseTest) {
                    testLeft = ((TrueFalseTest) test).questions.size();
                    //Log.i(TAG, "Test left " + testLeft);
                    i.setClass(getApplicationContext(), TrueFalseQuizActivity.class);
                }
                if (testLeft > 0) {
                    //Log.i(TAG, "Ci sono ancora " + testLeft + " quiz rimasti");
                    startActivity(i);
                }
                else {
                    //Log.i(TAG, "I test sono finiti");
                    int totalQuestions = i.getIntExtra("totalQuestions", 0);
                    finished = pathProgress.savedResult(startTime, new GregorianCalendar(), correctAnswers, totalQuestions); //if true ho finito il percorso
                    Log.i(TAG, "Domande totali: " + totalQuestions + "; domande corrette: " + correctAnswers);
                    if (!finished) {
                        i.setClass(getApplicationContext(), SearchNewStepActivity.class);
                    } else {
                        i.putExtra("totalScore", pathProgress.getTotalScore());
                        i.setClass(getApplicationContext(), ResultActivity.class);
                    }
                    startActivity(i);
                    //}
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
