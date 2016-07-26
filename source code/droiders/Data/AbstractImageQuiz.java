package Data;

/**
 * Created by andrea on 15/07/16.
 */
public abstract class AbstractImageQuiz implements AbstractQuiz {
   public final String pathImage, instructions;

   public AbstractImageQuiz(String instructions, String pathImage) {
      this.instructions = instructions;
      this.pathImage = pathImage;
   }
}
