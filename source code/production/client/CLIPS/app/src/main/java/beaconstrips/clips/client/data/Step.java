package beaconstrips.clips.client.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @file Step.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta una stazione diun percorso, contiene quindi un beacon di riferimento, una prova e una lista di beacon da usare come indicatori di prossimità della stazione durante la sua ricerca
 */
public class Step implements Serializable{
   public final Beacon stopBeacon;
   public final ArrayList<Proximity> proximities;
   public final Proof proof;
   public final String helpText;

   public Step(Beacon stopBeacon, ArrayList<Proximity> proximities, Proof proof, String helpText) {
      this.stopBeacon = stopBeacon;
      this.proximities = proximities;
      this.proof = proof;
      this.helpText = helpText;
   }
}
