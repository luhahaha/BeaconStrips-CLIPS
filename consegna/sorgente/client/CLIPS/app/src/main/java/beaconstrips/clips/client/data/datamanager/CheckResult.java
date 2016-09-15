package beaconstrips.clips.client.data.datamanager;

/**
 * @file CheckResult.java
 * @date 10/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta il risultato del controllo di un dato del profilo, contiene il tipo di dato controllato ("email", "username" o "password"), se il dato inviato è valido e, in caso negativo, perché non è valido
 */
public class CheckResult {
   public final String type;
   public final boolean isValid;
   public final String reason;
   public final String debugMessage;

   public CheckResult(String type, boolean isValid, String reason, String debugMessage) {
      this.type = type;
      this.isValid = isValid;
      this.reason = reason;
      this.debugMessage = debugMessage;
   }
}
