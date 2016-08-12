package beaconstrips.clips.client.data;

import java.util.List;

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
   public final List<Step> steps;

   public Path(int id, String startingMessage, String rewardMessage, List<Step> steps) {
      this.id = id;
      this.startingMessage = startingMessage;
      this.rewardMessage = rewardMessage;
      this.steps = steps;
   }
}
