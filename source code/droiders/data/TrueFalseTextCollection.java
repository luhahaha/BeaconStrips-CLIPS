package data;

import java.util.List;

/**
 * Created by andrea on 01/08/16.
 */
public class TrueFalseTextCollection extends TestCollection {
   public final List<TrueFalseText> questions;

   public TrueFalseTextCollection(boolean shuffleQuestions, List<TrueFalseText> questions) {
      super(shuffleQuestions);
      this.questions = questions;
   }
}
