package beaconstrips.clips;

import android.os.Parcel;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import beaconstrips.clips.client.data.Beacon;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.LinearScoringAlgorithm;
import beaconstrips.clips.client.data.MultipleChoiceTextQuiz;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.PathInfo;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.Proximity;
import beaconstrips.clips.client.data.Step;
import beaconstrips.clips.client.data.TrueFalseTextQuiz;
import beaconstrips.clips.client.pathprogress.RawBeacon;

/**
 * @file DataTest.java
 * @date 27/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU36 (Test di Unità 36). Verifica che i metodi di tutte le classi di data, ad eccezione di PathProgress, Utility e di quelle già verificate per gli altri package, funzionino correttamente.
 * In particolare le classi trattate sono MultipleChoiceTextQuiz, TrueFalseTextQuiz, Path, ProofResult, LinearScoringAlgorithm e Building.
 *
 *
 * Stampa attesa per il test "checkMultipleChoiceTextQuiz()": "Risultato di checkMultipleChoiceQuiz() per la risposta corretta: true"
 *                                                            "Risultato di checkMultipleChoiceQuiz() per la risposta errata: false"
 * Stampa attesa per il test "checkTrueFalseTextQuiz()": "Risultato di checkTrueFalseQuiz() per la risposta corretta: true"
 *                                                       "Risultato di checkTrueFalseQuiz() per la risposta errata: false"
 * Stampa attesa per il test "pathTest()": "Beacon uguali: true"
 *                                         "Beacon con UUID diverso: false"
 *                                         "Beacon con major diverso: false"
 *                                         "Beacon con minor diverso: false"
 * Stampa attesa per il test "proofResultTest()": "Stampa di getDuration(): 02:35:35.000"
 * Stampa attesa per il test "linearScoringAlgorithmTest()": "Stampa di getScore() standard: 24.0"
 *                                                           "Stampa di getScore() con il risultato a metà esatta: 20.0"
 *                                                           "Stampa di getScore() con le risposte tutte errate: 10.0"
 *                                                           "Stampa di getScore() con le risposte tutte esatte: 30.0"
 *                                                           "Stampa di getScore() con un tempo minore del minimo previsto: 34.0"
 *                                                           "Stampa di getScore() con un tempo maggiore del massimo previsto: 14.0"
 *                                                           "Stampa di getScore() senza tenere conto del tempo: 28.0"
 *                                                           "Stampa di getScore() senza tenere conto di quante risposte sono corrette: 20.0"
 *                                                           "Stampa di getScore() con un punteggio minimo e senza tempo minimo: 25.5"
 *                                                           "Stampa di getScore() con un punteggio minimo: 28.0"
 * Stampa attesa per il test "buildingTest()": "Risultati di getPathInfo():"
 *                                             "   Path 0:"
 *                                             "      ID: 1"
 *                                             "      Titolo: Primo percorso di prova"
 *                                             "      Descrizione: Percorso delle P"
 *                                             "      Destinatari: Per persone con il nome che comincia per P"
 *                                             "      Durata stimata: Poco"
 *                                             "      Posizione nella visualizzazione: 1"
 *                                             "   Path 1:"
 *                                             "      ID: 2"
 *                                             "      Titolo: Secondo percorso di prova"
 *                                             "      Descrizione: Percorso delle lettere miste"
 *                                             "      Destinatari: Per tutti, anche le persone il cui nome comincia per P"
 *                                             "      Durata stimata: Tanto"
 *                                             "      Posizione nella visualizzazione: 2"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class DataTest {
   @Test
   public void checkMultipleChoiceQuiz() {
      MultipleChoiceTextQuiz quiz = new MultipleChoiceTextQuiz("HELP", "Domanda di prova", "Risposta corretta", new String[]{"Risposta errata 1", "Risposta errata 2", "Risposta errata 3"});
      quiz.check("");
      Log.d("DataTest", "Risultato di checkMultipleChoiceQuiz per la risposta corretta: " + quiz.check("Risposta corretta"));
      Log.d("DataTest", "Risultato di checkMultipleChoiceQuiz per la risposta errata: " + quiz.check("Risposta errata 1"));
   }

   @Test
   public void checkTrueFalseQuiz() {
      TrueFalseTextQuiz quiz = new TrueFalseTextQuiz("HELP", "Domanda di prova", true);
      Log.d("DataTest", "Risultato di checkTrueFalseQuiz per la risposta corretta: " + quiz.check(true));
      Log.d("DataTest", "Risultato di checkTrueFalseQuiz per la risposta errata: " + quiz.check(false));
   }

   @Test
   public void pathTest() {
      try {
         JSONObject algorithm, test;
         algorithm = new JSONObject("{\"data\":{\"minScore\":1,\"maxScore\":20,\"minTime\":1,\"maxTime\":30,\"timeWeight\":1,\"accuracyWeight\":1}}");
         test = new JSONObject("{\"testType\": \"MultipleChoice\",\n" +
               "                    \"testTypeVersion\": 1,\n" +
               "                    \"data\": {\n" +
               "                        \"shuffleAnswers\": true,\n" +
               "                        \"shuffleQuestions\": false,\n" +
               "                        \"questions\": [\n" +
               "                            {\n" +
               "                                \"helpText\": \"Non mi pare fosse bianco!\",\n" +
               "                                \"question\": \"Di che colore era il cavallo bianco di Napoleone?\",\n" +
               "                                \"answers\": [\n" +
               "                                    \"Bianco\",\n" +
               "                                    \"Rosa\",\n" +
               "                                    \"Blu\",\n" +
               "                                    \"Rosso\"\n" +
               "                                ],\n" +
               "                                \"correctIndex\": 0\n" +
               "                            }]}}");
         ArrayList<Proximity> proximities = new ArrayList<>();
         proximities.add(new Proximity(new Beacon(2, "22abe428c83492abc2394cdd38abcb27", 22, 13), 33, "Sei a un terzo"));
         proximities.add(new Proximity(new Beacon(3, "22abe428c83492abc2394cdd38abcb27", 23, 11), 66, "Sei a due terzi"));
         ArrayList<Step> steps = new ArrayList<>();
         steps.add(new Step(new Beacon(1, "22abe428c83492abc2394cdd38abcb27", 15, 2), proximities, new Proof(1, "Prova di prova", "Prova a fare questa prova", algorithm, test), "Testo di aiuto"));
         Path path = new Path(1, "Messaggio iniziale", "Messaggio finale", steps);
         Beacon beacon = new Beacon(1, "22abe428c83492abc2394cdd38abcb27", 24, 19);
         Constructor<RawBeacon> constructor = RawBeacon.class.getDeclaredConstructor(int.class, int.class, String.class);
         constructor.setAccessible(true);
         Log.d("DataTest", "Beacon uguali: " + path.equal(beacon, constructor.newInstance(19, 24, "22abe428c83492abc2394cdd38abcb27")));
         Log.d("DataTest", "Beacon con UUID diverso: " + path.equal(beacon, constructor.newInstance(19, 24, "22abe428c83492abc2394cdd38abcb37")));
         Log.d("DataTest", "Beacon con major diverso: " + path.equal(beacon, constructor.newInstance(19, 25, "22abe428c83492abc2394cdd38abcb27")));
         Log.d("DataTest", "Beacon con minor diverso: " + path.equal(beacon, constructor.newInstance(18, 24, "22abe428c83492abc2394cdd38abcb27")));
      } catch(NoSuchMethodException e) {
         Log.d("DataTest", "Test fallito");
      } catch(IllegalAccessException e) {
         Log.d("DataTest", "Test fallito");
      } catch(InvocationTargetException e) {
         Log.d("DataTest", "Test fallito");
      } catch(JSONException e) {
         Log.d("DataTest", "Test fallito");
      }  catch(InstantiationException e) {
         Log.d("DataTest", "Test fallito");
      }
   }

   @Test
   public void proofResultTest() {
      ProofResult proofResult = new ProofResult(1, new GregorianCalendar(2016, 7, 29, 15, 24, 10), new GregorianCalendar(2016, 7, 29, 17, 59, 45), 25);
      SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss.SSS", Locale.ITALIAN);
      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
      Log.d("DataTest", "Stampa di getDuration(): " + dateFormat.format(proofResult.getDuration()));
   }

   @Test
   public void linearScoringAlgorithmtTest() { //TODO
      LinearScoringAlgorithm algorithm = new LinearScoringAlgorithm(0, 40, 10, 30, 1, 1);
      Log.d("DataTest", "Stampa di getScore() standard: " + algorithm.getScore(20, 7, 10));
      Log.d("DataTest", "Stampa di getScore() con il risultato a metà esatta: " + algorithm.getScore(20, 5, 10));
      Log.d("DataTest", "Stampa di getScore() con le risposte tutte errate: " + algorithm.getScore(20, 0, 10));
      Log.d("DataTest", "Stampa di getScore() con le risposte tutte esatte: " + algorithm.getScore(20, 10, 10));
      Log.d("DataTest", "Stampa di getScore() con un tempo minore del minimo previsto: " + algorithm.getScore(9, 7, 10));
      Log.d("DataTest", "Stampa di getScore() con un tempo maggiore del massimo previsto: " + algorithm.getScore(31, 7, 10));
      algorithm = new LinearScoringAlgorithm(0, 40, 10, 30, 0, 1);
      Log.d("DataTest", "Stampa di getScore() senza tenere conto del tempo: " + algorithm.getScore(20, 7, 10));
      algorithm = new LinearScoringAlgorithm(0, 40, 10, 30, 1, 0);
      Log.d("DataTest", "Stampa di getScore() senza tenere conto di quante risposte sono corrette: " + algorithm.getScore(20, 7, 10));
      algorithm = new LinearScoringAlgorithm(10, 40, 0, 30, 1, 1);
      Log.d("DataTest", "Stampa di getScore() con un punteggio minimo e senza tempo minimo: " + algorithm.getScore(20, 7, 10));
      algorithm = new LinearScoringAlgorithm(10, 40, 10, 30, 1, 1);
      Log.d("DataTest", "Stampa di getScore() con un punteggio minimo: " + algorithm.getScore(20, 7, 10));
   }

   @Test
   public void buildingTest() {
      ArrayList<PathInfo> pathinfos = new ArrayList<>();
      pathinfos.add(new PathInfo(1, "Primo percorso di prova", "Percorso delle P", "Per persone con il nome che comincia per P", "Poco", 1));
      pathinfos.add(new PathInfo(2, "Secondo percorso di prova", "Percorso delle lettere miste", "Per tutti, anche per le persone il cui nome comincia per P", "Tanto", 2));
      Building building = new Building("Edificio di prova", "Immagine edificio di prova", "Edificio per i test", "Edificio creato esclusivamente per i test", "Ogni volta che questo test si rivela necessario", "Indirizzo di prova", 5, 5, "0349423618", "prova@gmail.com", "Prova WhatsApp", "Prova Telegram", "Account Twitter", "Account Facebook", "www.prova.com", pathinfos);
      ArrayList<PathInfo> array = building.getPathInfo();
      Log.d("DataTest", "Risultati di getPathInfo():");
      for(int i=0; i<array.size(); i++) {
         Log.d("DataTest", "   Path " + i + ":");
         Log.d("DataTest", "      ID: " + array.get(i).id);
         Log.d("DataTest", "      Titolo: " + array.get(i).title);
         Log.d("DataTest", "      Descrizione: " + array.get(i).description);
         Log.d("DataTest", "      Destinatari: " + array.get(i).target);
         Log.d("DataTest", "      Durata stimata: " + array.get(i).estimatedDuration);
         Log.d("DataTest", "      Posizione nella visualizzazione: " + array.get(i).position);
      }
   }
}