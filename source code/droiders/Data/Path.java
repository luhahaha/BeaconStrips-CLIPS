public class Path {
   public final int id;
   public final String startingMessage;
   public final String rewardMessage;
   public final list<Step> steps;

   Path(int id, String startingMessage, String rewardMessage, list<Step> steps) {
      this.id=id;
      this.startingMessage=startingMessage;
      this.rewardMessage=rewardMessage;
      this.steps=steps;
   }
}
