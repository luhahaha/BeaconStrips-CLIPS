package Data;

/**
 * Created by andrea on 01/08/16.
 */
public abstract class TestCollection {
   boolean shuffleContents; //questions per i quiz, sets per i biggershape, games per i gamecollections

   public TestCollection(boolean shuffleContents) {
      this.shuffleContents = shuffleContents;
   }
}
