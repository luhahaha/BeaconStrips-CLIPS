package Data;

/**
 * Created by andrea on 20/07/16.
 */
public class ConcreteTrueFalse implements TestFactory {
   public TrueFalseImage createImageQuiz(String instructions, String pathImage, boolean response) {
      return new TrueFalseImage(instructions, pathImage, response);
   }

   public TrueFalseText createTextQuiz(String instructions, boolean response) {
      return new TrueFalseText(instructions, response);
   }
}