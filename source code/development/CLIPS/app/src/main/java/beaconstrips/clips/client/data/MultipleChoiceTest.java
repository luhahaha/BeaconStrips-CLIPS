package beaconstrips.clips.client.data;

import java.util.List;

/**
 * @file MultipleChoiceTest.java
 * @date 01/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta un test di MultipleChoiceTextQuiz e MultipleChoiceImageQuiz, shuffleQuestions indica se le domande devono essere poste nell'ordine in cui sono salvate o in modo misto, shuffleAnswers invece indica se l'ordine delle risposte dev'essere quello salvato o misto
 */
public class MultipleChoiceTest extends Test {
   public final boolean shuffleQuestions;
   public final boolean shuffleAnswers;
   public final List<MultipleChoiceTextQuiz> questions;

   public MultipleChoiceTest(boolean shuffleQuestions, boolean shuffleAnswers, List<MultipleChoiceTextQuiz> questions) {
      this.shuffleQuestions = shuffleQuestions;
      this.shuffleAnswers = shuffleAnswers;
      this.questions = questions;
   }
}
