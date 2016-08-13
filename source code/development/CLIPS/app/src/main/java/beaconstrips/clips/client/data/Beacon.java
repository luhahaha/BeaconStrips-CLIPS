package beaconstrips.clips.client.data;

/**
 * @file Beacon.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe che rappresenta i dati di un beacon
 */

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