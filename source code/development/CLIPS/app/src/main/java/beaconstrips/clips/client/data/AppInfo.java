package beaconstrips.clips.client.data;

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