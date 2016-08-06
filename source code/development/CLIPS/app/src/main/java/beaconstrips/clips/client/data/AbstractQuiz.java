package beaconstrips.clips.client.data;

/**
 * Created by andrea on 15/07/16.
 */
public abstract class AbstractQuiz {
   public final String helpText;
   public final String instructions;

   public AbstractQuiz(String helpText, String instructions) {
      this.helpText = helpText;
      this.instructions = instructions;
   }
}
