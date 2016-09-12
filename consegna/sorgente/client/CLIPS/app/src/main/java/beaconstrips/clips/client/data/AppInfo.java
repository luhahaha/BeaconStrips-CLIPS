package beaconstrips.clips.client.data;

/**
 * @file AppInfo.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta i dati generali dell'applicazione
 */
public class AppInfo {
   public final String description;
   public final String supportEmail;
   public final String website;
   public final String UUID;

   public AppInfo(String description, String supportEmail, String website, String UUID) {
      this.description = description;
      this.supportEmail = supportEmail;
      this.website = website;
      this.UUID = UUID;
   }
}