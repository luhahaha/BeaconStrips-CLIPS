package Data;

import java.util.List;

/**
 * Created by andrea on 08/07/16.
 */
public class PathResult { //per non perderci troppo tempo intanto ho buttato giù una classe di prova che non ha niente a che fare con la classe originale, in caso contrario bisogna definire 2 classi perché anche le prove sono incluse
   public String scrittaDiProva; //prova per i campi dati
   public List<String> elencoDiProva; //vettore per i campi dati

   PathResult() {
      scrittaDiProva = "Se vedi questa scritta allora la classe funziona.";
      for (int i = 0; i < 20; i++) {
         String scritta = "Questo è l'elemento n° " + i;
         elencoDiProva.add(i, scritta);
      }
   }
}
