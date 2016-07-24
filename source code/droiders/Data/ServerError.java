package Data;

/**
 * Created by andrea on 24/07/16.
 */
public class ServerError {
   public int errorCode;
   public String message;

   public ServerError(int errorCode, String message) {
      this.errorCode = errorCode;
      this.message = message;
   }
}
