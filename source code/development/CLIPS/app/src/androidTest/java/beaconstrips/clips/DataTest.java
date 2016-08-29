package beaconstrips.clips;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

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
import beaconstrips.clips.client.data.MultipleChoiceTextQuiz;
import beaconstrips.clips.client.data.Path;
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
 * classe che contiene il TU31 (Test di Unità 31). Verifica che il metodo check, con cui si verifica se la risposta data dall'utente è corretta o no, funziona come previsto.
 * Verranno effettuate due prove per metodo, una in cui la risposta inviata è esatta e l'altra in cui è errata.
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
 *                                         "Proximity di un beacon esatto:"
 *                                         "   Beacon:"
 *                                         "      ID del beacon: 3"
 *                                         "      UUID del beacon: 22abe428c83492abc2394cdd38abcb27"
 *                                         "      Major del beacon: 23"
 *                                         "      Minor del beacon: 11"
 *                                         "   Percentuale: 66.0"
 *                                         "   Testo da mostrare: Sei a due terzi"
 *                                         "Proximity di un beacon errato: null"
 * Stampa attesa per il test "proofResultTest()": "Stampa di getDuration(): 02:35:35.000"
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
         steps.add(new Step(new Beacon(1, "22abe428c83492abc2394cdd38abcb27", 15, 2), proximities, new Proof(1, "Prova di prova", "Prova a fare questa prova", algorithm, test)));
         Path path = new Path(1, "Messaggio iniziale", "Messaggio finale", steps);
         Beacon beacon = new Beacon(1, "22abe428c83492abc2394cdd38abcb27", 24, 19);
         Constructor<RawBeacon> constructor = RawBeacon.class.getDeclaredConstructor(int.class, int.class, String.class);
         constructor.setAccessible(true);
         Log.d("DataTest", "Beacon uguali: " + path.equal(beacon, constructor.newInstance(19, 24, "22abe428c83492abc2394cdd38abcb27")));
         Log.d("DataTest", "Beacon con UUID diverso: " + path.equal(beacon, constructor.newInstance(19, 24, "22abe428c83492abc2394cdd38abcb37")));
         Log.d("DataTest", "Beacon con major diverso: " + path.equal(beacon, constructor.newInstance(19, 25, "22abe428c83492abc2394cdd38abcb27")));
         Log.d("DataTest", "Beacon con minor diverso: " + path.equal(beacon, constructor.newInstance(18, 24, "22abe428c83492abc2394cdd38abcb27")));
         Proximity proximity = path.searchProximity(constructor.newInstance(11, 23, "22abe428c83492abc2394cdd38abcb27"));
         Log.d("DataTest", "Proximity di un beacon esatto:");
         Log.d("DataTest", "   Beacon: ");
         Log.d("DataTest", "      ID del beacon: " + proximity.beacon.id);
         Log.d("DataTest", "      UUID del beacon: " + proximity.beacon.UUID);
         Log.d("DataTest", "      Major del beacon: " + proximity.beacon.major);
         Log.d("DataTest", "      Minor del beacon: " + proximity.beacon.minor);
         Log.d("DataTest", "   Percentuale: " + proximity.percentage);
         Log.d("DataTest", "   Testo da mostrare: " + proximity.textToDisplay);
         Log.d("DataTest", "Proximity di un beacon errato: " + path.searchProximity(constructor.newInstance(18, 24, "22abe428c83492abc2394cdd38abcb27")));
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
}
