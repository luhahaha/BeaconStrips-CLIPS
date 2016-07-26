package Data;

public class Proximity {
   public final Beacon beacon;
   public final float percentage;
   public final String textToDisplay;
   public final int id;

   public Proximity(Beacon beacon, float percentage, String textToDisplay, int id) {
      this.beacon = beacon;
      this.percentage = percentage;
      this.textToDisplay = textToDisplay;
      this.id = id;
   }
}