package beaconstrips.clips;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import beaconstrips.clips.client.data.MultipleChoiceTextQuiz;
import beaconstrips.clips.client.data.TrueFalseTextQuiz;

/**
 * @file CheckQuizTest.java
 * @date 27/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU31 (Test di Unità 31). Verifica che il metodo check, con cui si verifica se la risposta data dall'utente è corretta o no, funziona come previsto.
 * Verranno effettuate due prove per metodo, una in cui la risposta inviata è esatta e l'altra in cui è errata.
 *
 *
 * Stampa attesa per il test "checkMultipleChoiceTextQuiz()": "Risultato di checkMultipleChoiceQuiz() per la risposta corretta: true"
 *                                                            "Risultato di checkMultipleChoiceQuiz() per la risposta errata: false"
 * Stampa attesa per il test "checkTrueFalseTextQuiz()": "Risultato di checkTrueFalseQuiz() per la risposta corretta: true"
 *                                                       "Risultato di checkTrueFalseQuiz() per la risposta errata: false"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CheckQuizTest {
   @Test
   public void checkMultipleChoiceQuiz() {
      MultipleChoiceTextQuiz quiz = new MultipleChoiceTextQuiz("HELP", "Domanda di prova", "Risposta corretta", new String[]{"Risposta errata 1", "Risposta errata 2", "Risposta errata 3"});
      quiz.check("");
      Log.d("CheckQuizTest", "Risultato di checkMultipleChoiceQuiz per la risposta corretta: " + quiz.check("Risposta corretta"));
      Log.d("CheckQuizTest", "Risultato di checkMultipleChoiceQuiz per la risposta errata: " + quiz.check("Risposta errata 1"));
   }

   @Test
   public void checkTrueFalseQuiz() {
      TrueFalseTextQuiz quiz = new TrueFalseTextQuiz("HELP", "Domanda di prova", true);
      Log.d("CheckQuizTest", "Risultato di checkTrueFalseQuiz per la risposta corretta: " + quiz.check(true));
      Log.d("CheckQuizTest", "Risultato di checkTrueFalseQuiz per la risposta errata: " + quiz.check(false));
   }
}
