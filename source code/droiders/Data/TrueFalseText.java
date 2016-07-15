package Data;

/**
 * Created by andrea on 15/07/16.
 */
public class TrueFalseText extends AbstractQuiz {
   Boolean response; //Dato che la domanda è booleana basta un dato boolean per rappresentare la risposta

   TrueFalseText(String instructions, boolean response) {
      super(instructions);
      this.response=response;
   }

   boolean check(boolean userResponse) {
      return response==userResponse;
   }
}
