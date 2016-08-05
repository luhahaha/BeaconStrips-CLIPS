package beaconstrips.clips.data;

/**
 * Created by andrea on 15/07/16.
 */
public class MultipleChoiceImageQuiz extends MultipleChoiceTextQuiz {
   public final String image; //contiene il nome dell'immagine

   public MultipleChoiceImageQuiz(String helpText, String instructions, String trueResponse, String[] falseResponse, String image) {
      super(helpText, instructions, trueResponse, falseResponse);
      this.image = image;
   }
}
