package data;

import java.util.List;

/**
 * Created by andrea on 02/08/16.
 */
public class BiggerShapeTest extends Test {
   public final String instructions;
   public final boolean shuffleSets;
   public final List<BiggerShape> games;

   public BiggerShapeTest(String instructions, boolean shuffleSets, List<BiggerShape> games) {
      this.instructions = instructions;
      this.shuffleSets = shuffleSets;
      this.games = games;
   }
}
