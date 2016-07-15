package Data;

/**
 * Created by andrea on 15/07/16.
 */
public abstract class AbstractImageQuiz extends AbstractQuiz {
   String pathImage;

   AbstractImageQuiz(String instructions, String pathImage) {
      super(instructions);
      this.pathImage=pathImage;
   }
}
