package beaconstrips.clips.urlrequest;

/**
 * Created by andrea on 24/07/16.
 */
public class ServerError {
   public int errorCode;
   public String debugMessage;
   public String userMessage;

   public ServerError(int errorCode, String debugMessage, String userMessage) {
      this.errorCode = errorCode;
      this.debugMessage = debugMessage;
      this.userMessage = userMessage;
   }
}
