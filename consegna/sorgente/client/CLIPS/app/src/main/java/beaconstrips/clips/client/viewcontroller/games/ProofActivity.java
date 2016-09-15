/**
 * @file ProofActivity.java
 * @date 2016-07-17
 * @version 1.10
 * @author Viviana Alessio
 * Gestisce l'activity della prova
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.GameCollection;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.Test;
import beaconstrips.clips.client.data.TrueFalseTest;

public class ProofActivity extends AppCompatActivity {

    private String TAG = "ProofActivity";
    private Test test;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof);
        i = getIntent();
        test = (Test) i.getSerializableExtra("test");
        ((TextView) findViewById(R.id.titleLabel)).setText(i.getStringExtra("proofTitle"));
        ((TextView) findViewById(R.id.instructionLabel)).setText(i.getStringExtra("proofInstructions"));


        setQuiz();
        setButton();
    }

    private void setButton() {
        Button startProof = (Button) findViewById(R.id.startButton);
        startProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }

    private void setQuiz() {
        if (test instanceof GameCollection) {
            i.putExtra("gameCollection", test);
            test = ((GameCollection) test).games.remove(0);
        }

        if (test instanceof MultipleChoiceTest) {
            i.setClass(getApplicationContext(), MultipleChoiceQuizActivity.class);
            i.putExtra("totalQuestions", ((MultipleChoiceTest) test).questions.size());

        }
        if (test instanceof TrueFalseTest) {
            i.setClass(getApplicationContext(), TrueFalseQuizActivity.class);
            i.putExtra("totalQuestions", ((TrueFalseTest) test).questions.size());
        }
    }
}
