package beaconstrips.clips.client.data;

public class Proximity {
   public final Beacon beacon;
   public final double percentage;
   public final String textToDisplay;

   public Proximity(Beacon beacon, double percentage, String textToDisplay) {
      this.beacon = beacon;
      this.percentage = percentage;
      this.textToDisplay = textToDisplay;
   }
}