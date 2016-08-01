package Data;

/**
 * Created by andrea on 15/07/16.
 */
public class MultipleChoiceImage extends AbstractImageQuiz {
   String trueResponse; //Qui è contenuto il testo della risposta
   String falseResponse[]; //In teoria il numero di risposte che si possono inserire sono infinite, io intanto volevo inserirne 3 (4 in tutto), alla QuizDuello per intenderci

   public MultipleChoiceImage(String helpText, String instructions, String pathImage, String trueResponse, String falseResponse[]) {
      super(helpText, instructions, pathImage);
      this.trueResponse = trueResponse;
      this.falseResponse = falseResponse;
   }

   public boolean check(String userResponse) {
      return trueResponse.equals(userResponse);
   }
}
