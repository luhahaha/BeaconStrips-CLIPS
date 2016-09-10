package beaconstrips.clips.client.data;

import java.io.Serializable;

/**
 * @file LinearScoringAlgorithm.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta l'algoritmo di calcolo del punteggio usato nella prova, basato sui parametri passati durante l'inizializzazione
 */
public class LinearScoringAlgorithm implements Serializable{
   public final int minScore;
   public final int maxScore;
   public final double minTime;
   public final double maxTime;
   public final double timeWeight;
   public final double accuracyWeight;

   public LinearScoringAlgorithm(int minScore, int maxScore, double minTime, double maxTime, double timeWeight, double accuracyWeight) {
      this.minScore = minScore;
      this.maxScore = maxScore;
      this.minTime = minTime;
      this.maxTime = maxTime;
      this.timeWeight = timeWeight;
      this.accuracyWeight = accuracyWeight;
   }

   public double getScore(double time, int correct, int total) { //correct e total indicano quante risposte corrette ci sono e quante ne sono previste in tutto
      if(minScore==0 && maxScore==0 && minTime==0 && maxTime==0 && timeWeight==0 && accuracyWeight==0) { //se c'è stato un errore in fase di parsing
         return 0;
      }
      double weightSum = this.timeWeight + this.accuracyWeight;
      double deltaScore = this.maxScore - this.minScore;
      double deltaTime = this.maxTime - this.minTime;

      if(deltaTime==0) { //se c'è minTime==maxTime l'equazione sotto restituisce NaN, quindi devo fare un'equazione a parte senza il tempo
         return ((double) correct / (double) total) * deltaScore + this.minScore;
      }
      return deltaScore * ((1 - ((Math.min(Math.max(time, this.minTime), this.maxTime)) - this.minTime) / deltaTime) * this.timeWeight + (double) correct / (double) total * this.accuracyWeight) / (weightSum) + this.minScore;
   }
}
