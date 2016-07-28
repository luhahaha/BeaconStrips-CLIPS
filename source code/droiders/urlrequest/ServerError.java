package urlrequest;

/**
 * Created by andrea on 24/07/16.
 */
public class ServerError {
   public int errorCode;
   public String userMessage;
   public String debugMessage;

   public ServerError(int errorCode, String userMessage, String debugMessage) {
      this.errorCode = errorCode;
      this.userMessage = userMessage;
      this.debugMessage = debugMessage;
   }
}
