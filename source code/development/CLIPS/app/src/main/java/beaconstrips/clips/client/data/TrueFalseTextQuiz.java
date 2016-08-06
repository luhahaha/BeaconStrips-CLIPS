package beaconstrips.clips.client.data;

/**
 * Created by andrea on 15/07/16.
 */
public class TrueFalseTextQuiz extends AbstractQuiz {
   public final Boolean response; //Dato che la domanda è booleana basta un dato boolean per rappresentare la risposta

   public TrueFalseTextQuiz(String helpText, String instructions, boolean response) {
      super(helpText, instructions);
      this.response = response;
   }

   public boolean check(boolean userResponse) {
      return response == userResponse;
   }
}
