package Data;

/**
 * Created by andrea on 15/07/16.
 */
public class MultipleChoiceText extends AbstractTextQuiz {
   String trueResponse; //Qui è contenuto il testo della risposta
   String falseResponse[]; //In teoria il numero di risposte che si possono inserire sono infinite, io intanto volevo inserirne 3 (4 in tutto), alla QuizDuello per intenderci

   public MultipleChoiceText(String instructions, String trueResponse, String falseResponse[]) {
      super(instructions);
      this.trueResponse = trueResponse;
      this.falseResponse = falseResponse;
   }

   public boolean check(String userResponse) {
      return trueResponse.equals(userResponse);
   }
}
