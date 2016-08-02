package data;

/**
 * Created by andrea on 02/08/16.
 */
public class BiggerShape extends Test {
   public final Set[] sets;

   public static class Set {
      public final String shape;
      public final int left;
      public final int right;

      public Set(String shape, int left, int right) {
         this.shape = shape;
         this.left = left;
         this.right = right;
      }

      public double getArea() {
         double area;
         switch(shape) {
            case "circle": {
               area = Math.PI * Math.pow(((right-left)/2), 2);
            }
            case "square": {
               area = Math.pow(right-left, 2);
            }
            case "triangle": { //triangolo equilatero
               double height = Math.sqrt(Math.pow(right-left, 2) - Math.pow((right-left)/2, 2));
               area = (right-left) * height / 2;
            }
            default: area = 0;
         }
         return area;
      }
   }

   public BiggerShape(String helpText, Set[] sets) {
      super(helpText);
      this.sets = sets;
   }

   public boolean check(int setIndex) { //è l'indice della figura scelta dell'array sets
      boolean rightAnswer = true;
      double answerArea = sets[setIndex].getArea();
      for(int i=0; i<sets.length && rightAnswer!=false; i++) {
         if(i!=setIndex) { //se non è il valore scelto stesso
            if(answerArea<sets[i].getArea()) {
               rightAnswer = false;
            }
         }
      }
      return rightAnswer;
   }
}
