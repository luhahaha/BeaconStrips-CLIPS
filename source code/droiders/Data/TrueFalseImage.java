package Data;

/**
 * Created by andrea on 15/07/16.
 */
public class TrueFalseImage extends AbstractImageQuiz {
   Boolean response; //Dato che la domanda è booleana basta un dato boolean per rappresentare la risposta

   TrueFalseImage(String instructions, String pathImage, boolean response) {
      super(instructions, pathImage);
      this.response=response;
   }

   boolean check(boolean userResponse) {
      return response==userResponse;
   }
}
