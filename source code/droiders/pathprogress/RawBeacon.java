package pathprogress;

public class RawBeacon{
  public int minor,major;
  public String UUID;

  RawBeacon(int, minor, int major, String uuid){
    this.minor=minor;
    this.major=major;
    this.UUID=uuid;
  }
}
