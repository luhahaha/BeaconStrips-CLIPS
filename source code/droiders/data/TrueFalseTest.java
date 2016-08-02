package data;

import java.util.List;

/**
 * Created by andrea on 01/08/16.
 */
public class TrueFalseTest extends Test {
   public final boolean shuffleQuestions;
   public final List<TrueFalseTextQuiz> questions;

   public TrueFalseTest(boolean shuffleQuestions, List<TrueFalseTextQuiz> questions) {
      this.shuffleQuestions = shuffleQuestions;
      this.questions = questions;
   }
}
