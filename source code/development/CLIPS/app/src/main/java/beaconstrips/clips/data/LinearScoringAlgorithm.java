package beaconstrips.clips.data;

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

   public double getScore(double time, int correct, int total) {
      double weightSum = this.timeWeight + this.accuracyWeight;
      double deltaScore = this.maxScore - this.minScore;
      double deltaTime = this.maxTime - this.minTime;

      return deltaScore * ((1 - (Math.min(Math.max(time, this.minTime), this.maxTime)) / deltaTime) * this.timeWeight + (double) correct / (double) total * this.accuracyWeight) / (weightSum) + this.minScore;
   }
}
