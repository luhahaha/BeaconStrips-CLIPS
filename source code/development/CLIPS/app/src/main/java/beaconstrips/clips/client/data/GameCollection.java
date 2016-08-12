package beaconstrips.clips.client.data;

import java.util.List;

/**
 * @file GameCollection.java
 * @date 01/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta una collezione di test, in questo modo è possibile eseguire più test per la stessa prova; shuffleGames indica se i test devono essere eseguiti nell'ordine in cui sono salvati o in modo misto
 */
public class GameCollection extends Test {
   public final boolean shuffleGames;
   public final List<Test> games;

   public GameCollection(boolean shuffleGames, List<Test> games) {
      this.shuffleGames = shuffleGames;
      this.games = games;
   }
}
