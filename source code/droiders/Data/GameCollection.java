package Data;

import java.util.List;

/**
 * Created by andrea on 01/08/16.
 */
public class GameCollection extends TestCollection {
   List<TestCollection> games;

   public GameCollection(boolean shuffleGames, List<TestCollection> games) {
      super(shuffleGames);
      this.games = games;
   }
}
