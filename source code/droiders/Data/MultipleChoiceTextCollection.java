package Data;

import java.util.List;

/**
 * Created by andrea on 01/08/16.
 */
public class MultipleChoiceTextCollection extends MultipleChoiceCollection {
   List<MultipleChoiceText> questions;

   MultipleChoiceTextCollection(boolean shuffleQuestions, boolean shuffleAnswers, List<MultipleChoiceText> questions) {
      super(shuffleQuestions, shuffleAnswers);
      this.questions = questions;
   }
}
