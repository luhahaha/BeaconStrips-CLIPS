package Data;

/**
 * Created by andrea on 20/07/16.
 */
public class ConcreteMultipleChoice {
   public MultipleChoiceImage createImageQuiz(String instructions, String pathImage, String trueResponse, String falseResponse[]) {
      return new MultipleChoiceImage(instructions, pathImage, trueResponse, falseResponse);
   }

   public MultipleChoiceText createTextQuiz(String instructions, String trueResponse, String falseResponse[]) {
      return new MultipleChoiceText(instructions, trueResponse, falseResponse);
   }
}
