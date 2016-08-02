package Data;

import java.util.List;

/**
 * Created by andrea on 01/08/16.
 */
public class MultipleChoiceImageCollection extends MultipleChoiceCollection {
   public final List<MultipleChoiceImage> questions;

   MultipleChoiceImageCollection(boolean shuffleQuestions, boolean shuffleAnswers, List<MultipleChoiceImage> questions) {
      super(shuffleQuestions, shuffleAnswers);
      this.questions = questions;
   }
}
