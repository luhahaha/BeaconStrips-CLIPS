package beaconstrips.clips.client.data;

public class Beacon {
   public final int id;
   public final String UUID;
   public final int major;
   public final int minor;

   public Beacon(int id, String UUID, int major, int minor) {
      this.id = id;
      this.UUID = UUID;
      this.major = major;
      this.minor = minor;
   }
}