package beaconstrips.clips.client.data;

import java.io.Serializable;
import java.util.List;

/**
 * @file BiggerShapeTest.java
 * @date 02/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta un test di BiggerShape, il parametro instructions contiene la descrizione da mostrare all'utente prima dell'avvio del test, per spiegare cosa deve fare; invece shuffleSets indica se i giochi vanno eseguiti nell'ordine in cui sono salvati o in modo misto
 */
public class BiggerShapeTest extends Test implements Serializable{
   public final String instructions;
   public final boolean shuffleSets;
   public final List<BiggerShape> games;

   public BiggerShapeTest(String instructions, boolean shuffleSets, List<BiggerShape> games) {
      this.instructions = instructions;
      this.shuffleSets = shuffleSets;
      this.games = games;
   }
}
