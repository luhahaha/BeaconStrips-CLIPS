package Data;

/**
 * Created by andrea on 24/07/16.
 */
public class ResponseStatus { //potrei usare un generic qui e inserire l'eventuale oggetto da ritornare
   public int errorCode;
   public String userMessage;
   public String debugMessage;

   public ResponseStatus(int errorCode, String userMessage, String debugMessage) {
      this.errorCode = errorCode;
      this.userMessage = userMessage;
      this.debugMessage = debugMessage;
   }
}
