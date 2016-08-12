package beaconstrips.clips.client.data;

import java.util.List;

/**
 * @file TrueFalseTest.java
 * @date 01/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene un test di TrueFalseImageQuiz e TrueFalseTextQuiz, il parametro shuffleQuestions indica se le domande devono essere poste nell'ordine in cui sono salvate o in modo misto
 */
public class TrueFalseTest extends Test {
   public final boolean shuffleQuestions;
   public final List<TrueFalseTextQuiz> questions;

   public TrueFalseTest(boolean shuffleQuestions, List<TrueFalseTextQuiz> questions) {
      this.shuffleQuestions = shuffleQuestions;
      this.questions = questions;
   }
}
