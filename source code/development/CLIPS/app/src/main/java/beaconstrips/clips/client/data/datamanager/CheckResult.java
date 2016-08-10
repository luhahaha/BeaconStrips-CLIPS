package beaconstrips.clips.client.data.datamanager;

/**
 * Created by andrea on 10/08/16.
 */
public class CheckResult {
   public final boolean isValid;
   public final String reason;
   public final String debugMessage;

   public CheckResult(boolean isValid, String reason, String debugMessage) {
      this.isValid = isValid;
      this.reason = reason;
      this.debugMessage = debugMessage;
   }
}
