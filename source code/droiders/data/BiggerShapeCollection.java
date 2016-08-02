package data;

import java.util.List;

/**
 * Created by andrea on 02/08/16.
 */
public class BiggerShapeCollection extends TestCollection {
   public final List<BiggerShape> games;

   public BiggerShapeCollection(boolean shuffleSets, List<BiggerShape> games) {
      super(shuffleSets);
      this.games = games;
   }
}
