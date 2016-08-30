package beaconstrips.clips.client.data;

/**
 * @file LinearScoringAlgorithm.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta l'algoritmo di calcolo del punteggio usato nella prova, basato sui parametri passati durante l'inizializzazione
 */
public class LinearScoringAlgorithm {
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
      double weightSum = this.timeWeight + this.accuracyWeight;
      double deltaScore = this.maxScore - this.minScore;
      double deltaTime = this.maxTime - this.minTime;

      return deltaScore * (((Math.min(Math.max(time, this.minTime), this.maxTime)) / deltaTime) * this.timeWeight + (double) correct / (double) total * this.accuracyWeight) / (weightSum) + this.minScore;
   }
}
