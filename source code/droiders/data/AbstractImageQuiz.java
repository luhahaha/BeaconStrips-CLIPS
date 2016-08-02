package data;

/**
 * Created by andrea on 15/07/16.
 */
public abstract class AbstractImageQuiz extends AbstractTextQuiz {
   public final String pathImage;

   public AbstractImageQuiz(String helpText, String instructions, String pathImage) {
      super(helpText, instructions);
      this.pathImage = pathImage;
   }
}
