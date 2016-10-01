package beaconstrips.clips.client.data;

import java.io.Serializable;

/**
 * @file Proximity.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe che rappresenta i dati della distanza dell'utente da un beacon
 */

public class Proximity implements Serializable{
   public final Beacon beacon;
   public final double percentage;
   public final String textToDisplay;

   public Proximity(Beacon beacon, double percentage, String textToDisplay) {
      this.beacon = beacon;
      this.percentage = percentage;
      this.textToDisplay = textToDisplay;
   }
}