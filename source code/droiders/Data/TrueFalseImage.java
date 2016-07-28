package Data;

/**
 * Created by andrea on 15/07/16.
 */
public class TrueFalseImage extends AbstractImageQuiz {
   Boolean response; //Dato che la domanda è booleana basta un dato boolean per rappresentare la risposta

   public TrueFalseImage(String instructions, String pathImage, boolean response) {
      super(instructions, pathImage);
      this.response = response;
   }

   public boolean check(boolean userResponse) {
      return response == userResponse;
   }
}
