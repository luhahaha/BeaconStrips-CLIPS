package beaconstrips.clips.client.data;

import java.io.Serializable;

/**
 * @file Score.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta il punteggio e la posizione in classifica dell'utente
 */
public class Score implements Serializable{
   public final String username;
   public final int position;
   public final int score;

   public Score(String username, int position, int score) {
      this.username = username;
      this.position = position;
      this.score = score;
   }
}