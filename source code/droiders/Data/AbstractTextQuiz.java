package Data;

/**
 * Created by andrea on 15/07/16.
 */
public abstract class AbstractTextQuiz extends Test {
   public final String instructions;

   public AbstractTextQuiz(String helpText, String instructions) {
      super(helpText);
      this.instructions = instructions;
   }
}
