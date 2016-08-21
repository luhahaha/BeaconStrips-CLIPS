/**
 * @file TrueFalseQuiz.java
 * @date 2016-07-18
 * @version 1.00
 * @author Viviana Alessio
 * Gestisce l'activity per i quiz vero/falso
 */

package beaconstrips.clips.client.viewcontroller.games;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import beaconstrips.clips.R;

public class TrueFalseQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false_quiz);
    }
}
