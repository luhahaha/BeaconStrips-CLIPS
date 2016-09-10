/**
 * @file TrueFalseQuizActivity.java
 * @date 2016-07-18
 * @version 1.00
 * @author Viviana Alessio
 * Gestisce l'activity per i quiz vero/falso
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.TrueFalseTest;

public class TrueFalseQuizActivity extends AppCompatActivity {

    private final String TAG = "TrueFalseQuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false_quiz);
        Intent i = getIntent();
        TrueFalseTest test = (TrueFalseTest) i.getSerializableExtra("test");
        Log.i(TAG, "Size of " + test.questions.size());
    }
}
