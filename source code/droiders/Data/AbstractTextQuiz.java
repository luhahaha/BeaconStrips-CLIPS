package Data;

/**
 * Created by andrea on 15/07/16.
 */
public abstract class AbstractTextQuiz implements AbstractQuiz {
   public final String instructions;

   public AbstractTextQuiz(String instructions) {
      this.instructions = instructions;
   }
}
