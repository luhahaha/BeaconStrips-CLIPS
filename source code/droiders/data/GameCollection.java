package data;

import java.util.List;

/**
 * Created by andrea on 01/08/16.
 */
public class GameCollection extends Test {
   public final boolean shuffleGames;
   public final List<Test> games;

   public GameCollection(boolean shuffleGames, List<Test> games) {
      this.shuffleGames = shuffleGames;
      this.games = games;
   }
}
