package Data;

/**
 * Created by andrea on 15/07/16.
 */
public class TrueFalseImage extends AbstractImageQuiz {
   public final Boolean response; //Dato che la domanda è booleana basta un dato boolean per rappresentare la risposta

   public TrueFalseImage(String helpText, String instructions, String pathImage, boolean response) {
      super(helpText, instructions, pathImage);
      this.response = response;
   }

   public boolean check(boolean userResponse) {
      return response == userResponse;
   }
}
