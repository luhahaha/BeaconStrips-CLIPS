package beaconstrips.clips.data;

/**
 * Created by andrea on 15/07/16.
 */
public class MultipleChoiceTextQuiz extends AbstractQuiz {
   public final String trueResponse; //Qui è contenuto il testo della risposta
   public final String falseResponse[];

   public MultipleChoiceTextQuiz(String helpText, String instructions, String trueResponse, String falseResponse[]) {
      super(helpText, instructions);
      this.trueResponse = trueResponse;
      this.falseResponse = falseResponse;
   }

   public boolean check(String userResponse) {
      return trueResponse.equals(userResponse);
   }
}
