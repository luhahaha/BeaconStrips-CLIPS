package data;

import java.util.List;

/**
 * Created by andrea on 01/08/16.
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
