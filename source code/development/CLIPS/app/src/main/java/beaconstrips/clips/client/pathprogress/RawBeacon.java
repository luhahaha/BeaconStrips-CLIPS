package beaconstrips.clips.client.pathprogress;

/**
 * @file RawBeacon.java
 * @date 22/07/16
 * @version 1.0.0
 * @author Luca Soldera
 *
 * classe che rappresenta i dati di un beacon fisico
 */

public class RawBeacon{
  public int minor,major;
  public String UUID;

  RawBeacon(int minor, int major, String uuid){
    this.minor=minor;
    this.major=major;
    this.UUID=uuid;
  }
}
