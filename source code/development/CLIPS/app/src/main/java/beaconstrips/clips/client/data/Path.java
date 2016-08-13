package beaconstrips.clips.client.data;

import java.util.ArrayList;

/**
 * @file Path.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe i dati di un percorso, ovvero il messaggio di inizio, quello di fine e l'elenco delle stazioni
 */
public class Path {
   public final int id;
   public final String startingMessage;
   public final String rewardMessage;
   public final ArrayList<Step> steps;

   public Path(int id, String startingMessage, String rewardMessage, ArrayList<Step> steps) {
      this.id = id;
      this.startingMessage = startingMessage;
      this.rewardMessage = rewardMessage;
      this.steps = steps;
   }
}
