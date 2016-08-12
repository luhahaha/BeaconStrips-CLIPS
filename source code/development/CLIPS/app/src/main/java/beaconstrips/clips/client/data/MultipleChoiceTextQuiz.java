package beaconstrips.clips.client.data;

/**
 * @file MultipleChoiceTextQuiz.java
 * @date 15/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta una domanda a risposta multipla con 4 scelte possibili
 */
public class MultipleChoiceTextQuiz extends AbstractQuiz {
   public final String trueResponse;
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
