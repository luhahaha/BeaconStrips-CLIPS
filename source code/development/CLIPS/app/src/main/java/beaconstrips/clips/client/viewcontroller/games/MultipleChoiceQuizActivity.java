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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.MultipleChoiceTextQuiz;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.ProofResult;

public class MultipleChoiceQuizActivity extends AppCompatActivity {

   private Path path;
   private int pathIndex;
   private final String TAG = "MultipleChoiceQuizAct";
   private MultipleChoiceTextQuiz answers;
   private Intent intent;
   MultipleChoiceTest test;
   private int correctAnswers;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_multiple_choice_quiz);
      //TODO insert from here choices with correct answer with list?
      intent = getIntent();

      test = (MultipleChoiceTest) intent.getSerializableExtra("test");
      answers = test.questions.remove(0);

      correctAnswers = intent.getIntExtra("correctAnswers", 0);

      //Log.i(TAG, "Size of" + test.questions.size());
      //Log.i(TAG, "Correct answer " + answers.trueResponse);


      setQuiz();

   }

   private void setQuiz() {
      //Log.i(TAG, answers.instructions);

      ((TextView) findViewById(R.id.questionLabel)).setText(answers.instructions);

      List<Integer> choices = new ArrayList<Integer>();
      choices.add(1);
      choices.add(2);
      choices.add(3);
      choices.add(4);
      Collections.shuffle(choices);
      intent.setClass(getApplicationContext(), ProofResultActivity.class);

      for(int i = 0; i < 4; i ++) {

         String choice = "choice" + String.valueOf(choices.get(i));


         int resID = this.getResources().getIdentifier(choice, "id", getPackageName());


         if(i == 0) {
            final Button rightAnswer = (Button) findViewById(resID);
            //Log.i(TAG, "Correct answer is on " + (choices.get(i) - 1));
            rightAnswer.setText(answers.trueResponse);
            rightAnswer.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {

                  intent.putExtra("quizLeft", test.questions.size());
                  intent.putExtra("correctAnswers", ++correctAnswers);
                  intent.putExtra("answer", true);
                  startActivity(intent);
               }
            });
         }
         else {
            Button wrongAnswer = (Button) findViewById(resID);
            wrongAnswer.setText(answers.falseResponse[i - 1]);
            wrongAnswer.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                  //TODO mostro suggerimento
                  intent.putExtra("answer", false);
                  startActivity(intent);
               }
            });
         }

      }

   }

   @Override
   public void onBackPressed() {
   }
}
