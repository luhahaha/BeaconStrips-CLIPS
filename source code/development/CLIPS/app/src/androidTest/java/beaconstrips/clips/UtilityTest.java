package beaconstrips.clips;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.PathInfo;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.Utility;

/**
 * @file UtilityTest.java
 * @date 30/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU38 (Test di Unità 38). Verifica che i metodi di Utility funzionino correttamente.
 *
 *
 * Stampa attesa per il test "pathProgressTest()": "Stampa di stringToGregorianCalendar(): 2016-08-25 15:39:05.000"
 *                                                 "Stampa di calculateTotalScore(): 43"
 *                                                 "" TODO: correggere addNearestBuilding e aggiungere l'esito del test
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class UtilityTest {
   @Test
   public void utilityTest() {
      String object = "2016-09-05T12:34:56.000Z";
      GregorianCalendar date = Utility.stringToGregorianCalendar(object);
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS", Locale.ITALIAN);
      Log.d("UtilityTest", "Stampa di stringToGregorianCalendar(): " + dateFormat.format(date.getTime()));
      ArrayList<ProofResult> resultArray = new ArrayList<>();
      resultArray.add(new ProofResult(1, new GregorianCalendar(2016, 7, 25, 9, 6, 12), new GregorianCalendar(2016, 7, 25, 9, 30, 13), 15));
      resultArray.add(new ProofResult(2, new GregorianCalendar(2016, 7, 25, 9, 35, 2), new GregorianCalendar(2016, 7, 25, 10, 2, 54), 25));
      resultArray.add(new ProofResult(3, new GregorianCalendar(2016, 7, 25, 10, 10, 32), new GregorianCalendar(2016, 7, 25, 10, 49, 24), 3));
      Log.d("UtilityTest", "Stampa di calculateTotalScore(): " + Utility.calculateTotalScore(resultArray));
      ArrayList<PathInfo> pathinfos = new ArrayList<>();
      pathinfos.add(new PathInfo(1, "Percorso 1", "Primo percorso", "Solo per i primi", "1 ora", 1));
      pathinfos.add(new PathInfo(2, "Percorso 2", "Secondo percorso", "Solo per i secondi", "2 ore", 2));
      Building building1 = new Building("Edificio 1", "Edificio numero 1", "Primo edificio", "Alle 1.00", "Via Uno, 1", 45, 10, "1111111111", "Uno@uno.uno", "UnoWA", "UnoT", "UnoTwitter", "UnoFB", "www.uno.com", pathinfos),
      building2 = new Building("Edificio 2", "Edificio numero 2", "Secondo edificio", "Alle 2.00", "Via Due, 2", 46, 11, "2222222222", "Due@dueo.due", "DueWA", "DueT", "DueTwitter", "DueFB", "www.due.com", pathinfos),
      building3 = new Building("Edificio 3", "Edificio numero 3", "Terzo edificio", "Alle 3.00", "Via Tre, 3", 46, 10, "3333333333", "Tre@tre.tre", "TreWA", "TreT", "TreTwitter", "TreFB", "www.tre.com", pathinfos),
      building4 = new Building("Edificio 4", "Edificio numero 4", "Quarto edificio", "Alle 4.00", "Via Quattro, 4", 45, 11, "4444444444", "Quattro@quattro.quattro", "QuattroWA", "QauttroT", "QuattroTwitter", "QuattroFB", "www.quattro.com", pathinfos);
      Building[] buildingArray = new Building[5];
      buildingArray[0] = building1;
      buildingArray[1] = building2;
      buildingArray = Utility.addNearestBuilding(building3, buildingArray, 45, 10);
      Log.d("UtilityTest", "Stampa di addNearestBuilding():");
      for(int i=0; i<buildingArray.length; i++) {
         Log.d("UtilityTest", "   " + buildingArray[i].name);
      }
      buildingArray = Utility.addNearestBuilding(building4, buildingArray, 45, 10);
      Log.d("UtilityTest", "Stampa di addNearestBuilding():");
      for(int i=0; i<buildingArray.length; i++) {
         Log.d("UtilityTest", "   " + buildingArray[i].name);
      }
   }
}
