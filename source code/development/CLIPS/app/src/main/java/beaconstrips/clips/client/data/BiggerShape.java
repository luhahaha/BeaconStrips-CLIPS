package beaconstrips.clips.client.data;

import java.io.Serializable;

/**
 * @file BiggerShape.java
 * @date 02/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta un gioco in cui bisogna scegliere la figura più grande tra le due mostrate; left e right rappresentano la dimensione della figura geometrica, shape indica il tipo di figura visualizzato, mentre helpText contiene il testo di aiuto da mostrare all'utente dopo un certo lasso di tempo
 */
public class BiggerShape implements Serializable{
   public final String helpText;
   public final String shape;
   public final int left;
   public final int right;

   public BiggerShape(String helpText, String shape, int left, int right) {
      this.helpText = helpText;
      this.shape = shape;
      this.left = left;
      this.right = right;
   }
}
