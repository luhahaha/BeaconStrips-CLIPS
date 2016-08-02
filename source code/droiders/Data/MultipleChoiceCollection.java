package Data;

/**
 * Created by andrea on 01/08/16.
 */
public abstract class MultipleChoiceCollection extends TestCollection {
   public final boolean shuffleAnswers;

   public MultipleChoiceCollection(boolean shuffleQuestions, boolean shuffleAnswers) {
      super(shuffleQuestions);
      this.shuffleAnswers = shuffleAnswers;
   }
}
