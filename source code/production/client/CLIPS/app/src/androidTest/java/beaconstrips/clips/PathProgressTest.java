package beaconstrips.clips;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import beaconstrips.clips.client.data.Beacon;
import beaconstrips.clips.client.data.BiggerShapeTest;
import beaconstrips.clips.client.data.GameCollection;
import beaconstrips.clips.client.data.LinearScoringAlgorithm;
import beaconstrips.clips.client.data.MultipleChoiceImageQuiz;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.PathProgress;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.Proximity;
import beaconstrips.clips.client.data.Step;
import beaconstrips.clips.client.data.TrueFalseImageQuiz;
import beaconstrips.clips.client.data.TrueFalseTest;

/**
 * @file PathProgressTest.java
 * @date 29/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU35 (Test di Unità 35). Verifica che i metodi di PathProgress funzionino correttamente.
 *
 *
 * Stampa attesa per il test "pathProgressTest()": "Stampa di getPath():"
 *                                                 "Dati sul percorso:"
 *                                                 "ID: 1"
 *                                                 "Messaggio d'inizio: Messaggio iniziale"
 *                                                 "Messaggio finale: Messaggio finale"
 *                                                 "Dati sullo step 0:"
 *                                                 "   Dati sul beacon di step:"
 *                                                 "      ID: 1"
 *                                                 "      UUID: 22abe428c83492abc2394cdd38abcb27"
 *                                                 "      Major: 15"
 *                                                 "      Minor: 2"
 *                                                 "   Dati sulla proximity 0:"
 *                                                 "      Dati sul beacon di proximity:"
 *                                                 "         ID: 2"
 *                                                 "         UUID: 22abe428c83492abc2394cdd38abcb27"
 *                                                 "         Major: 22"
 *                                                 "         Minor: 13"
 *                                                 "      Percentuale: 33.0"
 *                                                 "      Testo: Sei a un terzo"
 *                                                 "   Dati sulla proximity 1:"
 *                                                 "      Dati sul beacon di proximity:"
 *                                                 "         ID: 3"
 *                                                 "         UUID: 22abe428c83492abc2394cdd38abcb27"
 *                                                 "         Major: 23"
 *                                                 "         Minor: 11"
 *                                                 "      Percentuale: 66.0"
 *                                                 "      Testo: Sei a due terzi"
 *                                                 "   Dati su proof:"
 *                                                 "      ID: 1"
 *                                                 "      Testo iniziale: Prova a fare questa prova"
 *                                                 "      Titolo: Prova di prova"
 *                                                 "      Dati su algorithm:"
 *                                                 "         Punteggio minimo: 1"
 *                                                 "         Punteggio massimo: 20"
 *                                                 "         Tempo minimo: 1.0"
 *                                                 "         Tempo massimo: 30.0"
 *                                                 "         Peso del tempo: 1.0"
 *                                                 "         Peso delle risposte giuste: 1.0"
 *                                                 "      Dati su test:"
 *                                                 "        Dati su MultipleChoiceTest:"
 *                                                 "           Domande da mescolare: false"
 *                                                 "           Risposte da mescolare: true"
 *                                                 "           Domanda 0:"
 *                                                 "              Testo di aiuto: Non mi pare fosse bianco!"
 *                                                 "              Domanda: Di che colore era il cavallo bianco di Napoleone?"
 *                                                 "              Risposta giusta: Bianco"
 *                                                 "              Risposte errate: Rosa, Blu, Rosso"
 *                                                 "           Domanda 1:"
 *                                                 "              Testo di aiuto: 5 + 5 = 55"
 *                                                 "              Domanda: 5 + 5 = "
 *                                                 "              Risposta giusta: 10"
 *                                                 "              Risposte errate: 9, 11, 12"
 *                                                 "Stampa di getStartTime(): 2016-08-29T11:34:10.000Z"
 *                                                 "Stampa di getEndTime() dopo setEndTime(): 2016-08-29T13:15:02.000Z"
 *                                                 "Stampa di getDuration(): 01:40:52.000"
 *                                                 "Dati relativi ai risultati delle prove appena aggiunte:"
 *                                                 "   ID della prova: 1"
 *                                                 "   Orario di inizio: 09:40:23.000"
 *                                                 "   Orario di fine: 10:15:35.000"
 *                                                 "   Punteggio: 35"
 *                                                 "   ID della prova: 2"
 *                                                 "   Orario di inizio: 10:25:01.000"
 *                                                 "   Orario di fine: 11:01:10.000"
 *                                                 "   Punteggio: 17"
 *                                                 "Stampa di getTotalScore(): 52"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class PathProgressTest {
   @Test
   public void pathProgressTest() {
      JSONObject algorithm, test;
      try{
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
               "                            },\n" +
               "                            {\n" +
               "                                \"helpText\": \"5 + 5 = 55\",\n" +
               "                                \"question\": \"5 + 5 = \",\n" +
               "                                \"answers\": [\n" +
               "                                    \"9\",\n" +
               "                                    \"10\",\n" +
               "                                    \"11\",\n" +
               "                                    \"12\"\n" +
               "                                ],\n" +
               "                                \"correctIndex\": 1\n" +
               "                            }]}}");
         ArrayList<Proximity> proximities = new ArrayList<>();
         proximities.add(new Proximity(new Beacon(2, "22abe428c83492abc2394cdd38abcb27", 22, 13), 33, "Sei a un terzo"));
         proximities.add(new Proximity(new Beacon(3, "22abe428c83492abc2394cdd38abcb27", 23, 11), 66, "Sei a due terzi"));
         ArrayList<Step> steps = new ArrayList<>();
         steps.add(new Step(new Beacon(1, "22abe428c83492abc2394cdd38abcb27", 15, 2), proximities, new Proof(1, "Prova di prova", "Prova a fare questa prova", algorithm, test), "Testo di aiuto"));
         Path path = new Path(1, "Messaggio iniziale", "Messaggio finale", steps);
         PathProgress pathProgress = new PathProgress(path, new GregorianCalendar(2016, 7, 29, 11, 34, 10));
         Path pathPrint = pathProgress.getPath();
         Log.d("PathProgressTest", "Stampa di getPath():");
         printPath(pathPrint);
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN);
         Log.d("PathProgressTest", "Stampa di getStartTime(): " + dateFormat.format(pathProgress.getStartTime().getTime()));
         pathProgress.setEndTime(new GregorianCalendar(2016, 7, 29, 13, 15, 2));
         Log.d("PathProgressTest", "Stampa di getEndTime() dopo setEndTime(): " + dateFormat.format(pathProgress.getEndTime().getTime()));
         dateFormat = new SimpleDateFormat("kk:mm:ss.SSS", Locale.ITALIAN);
         dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
         Log.d("PathProgressTest", "Stampa di getDuration(): " + dateFormat.format(pathProgress.getDuration()));
         pathProgress.addProofResult(new ProofResult(1, new GregorianCalendar(2016, 7, 29, 11, 40, 23), new GregorianCalendar(2016, 7, 29, 12, 15, 35), 35));
         pathProgress.addProofResult(new ProofResult(2, new GregorianCalendar(2016, 7, 29, 12, 25, 1), new GregorianCalendar(2016, 7, 29, 13, 1, 10), 17)); //Nel percorso caricato c'è un'unica prova, non viene aggiunta la seconda per non appesantire il test, ovviamente questa incongruenza tra le prove effettuate e quelle salvate non deve avvenire nella realtà
         List<ProofResult> results = pathProgress.getProofResults();
         Log.d("PathProgressTest", "Dati relativi ai risultati delle prove appena aggiunte:");
         for(int i=0; i<results.size(); i++) {
            Log.d("PathProgressTest", "   ID della prova: " + results.get(i).id);
            Log.d("PathProgressTest", "   Orario di inizio: " + dateFormat.format(results.get(i).startTime.getTime()));
            Log.d("PathProgressTest", "   Orario di fine: " + dateFormat.format(results.get(i).endTime.getTime()));
            Log.d("PathProgressTest", "   Punteggio: " + results.get(i).score);
         }
         Log.d("PathProgressTest", "Stampa di getTotalScore(): " + pathProgress.getTotalScore());
      } catch(JSONException e) {
         Log.d("PathProgressTest", "Test errato");
      }
   }

   void printPath(Path path) {
      Log.d("PathProgressTest", "Dati sul percorso:");
      Log.d("PathProgressTest", "   ID: " + path.id);
      Log.d("PathProgressTest", "   Messaggio d'inizio: " + path.startingMessage);
      Log.d("PathProgressTest", "   Messaggio finale: " + path.rewardMessage);
      for(int i=0; i<path.steps.size(); i++) {
         Beacon stepBeacon = path.steps.get(i).stopBeacon;
         Proof stepProof = path.steps.get(i).proof;
         ArrayList<Proximity> stepProximities = path.steps.get(i).proximities;
         Log.d("PathProgressTest", "   Dati sullo step " + i + ":");
         Log.d("PathProgressTest", "      Dati sul beacon di step:");
         Log.d("PathProgressTest", "         ID: " + stepBeacon.id);
         Log.d("PathProgressTest", "         UUID: " + stepBeacon.UUID);
         Log.d("PathProgressTest", "         Major: " + stepBeacon.major);
         Log.d("PathProgressTest", "         Minor: " + stepBeacon.minor);
         for(int j=0; j<stepProximities.size(); j++) {
            Beacon proximityBeacon = stepProximities.get(j).beacon;
            Log.d("PathProgressTest", "      Dati sulla proximity " +j +":");
            Log.d("PathProgressTest", "         Dati sul beacon di proximity:");
            Log.d("PathProgressTest", "            ID: " + proximityBeacon.id);
            Log.d("PathProgressTest", "            UUID: " + proximityBeacon.UUID);
            Log.d("PathProgressTest", "            Major: " + proximityBeacon.major);
            Log.d("PathProgressTest", "            Minor: " + proximityBeacon.minor);
            Log.d("PathProgressTest", "         Percentuale: " + stepProximities.get(i).percentage);
            Log.d("PathProgressTest", "         Testo: " + stepProximities.get(i).textToDisplay);
         }
         Log.d("PathProgressTest", "      Dati su proof:");
         Log.d("PathProgressTest", "         ID: " + stepProof.id);
         Log.d("PathProgressTest", "         Testo iniziale: " + stepProof.instructions);
         Log.d("PathProgressTest", "         Titolo: " + stepProof.title);
         LinearScoringAlgorithm algorithm = stepProof.scoringAlgorithm;
         beaconstrips.clips.client.data.Test test = stepProof.test;
         Log.d("PathProgressTest", "         Dati su algorithm:");
         Log.d("PathProgressTest", "            Punteggio minimo: " + algorithm.minScore);
         Log.d("PathProgressTest", "            Punteggio massimo: " + algorithm.maxScore);
         Log.d("PathProgressTest", "            Tempo minimo: " + algorithm.minTime);
         Log.d("PathProgressTest", "            Tempo massimo: " + algorithm.maxTime);
         Log.d("PathProgressTest", "            Peso del tempo: " + algorithm.timeWeight);
         Log.d("PathProgressTest", "            Peso delle risposte giuste: " + algorithm.accuracyWeight);
         Log.d("PathProgressTest", "         Dati su test:");
         printTest(test, 4);
      }
   }

   public void printTest(beaconstrips.clips.client.data.Test test, int indLevel) { //metodo di supporto, indLevel indica il livello di indentazione, ogni unità corrisponde a 3 spazi
      String indentation = "";
      for(int i=0; i<indLevel; i++) {
         indentation += "   ";
      }
      if(test instanceof GameCollection) {
         GameCollection gameCollection = (GameCollection)test;
         Log.d("PathProgressTest", indentation + "Dati su GameCollection:");
         Log.d("PathProgressTest", indentation + "   Test da mescolare: " + gameCollection.shuffleGames);
         for(int j=0; j<gameCollection.games.size(); j++) {
            printTest(gameCollection.games.get(j), indLevel+1);
         }
      } else if(test instanceof MultipleChoiceTest) {
         MultipleChoiceTest mcTest = (MultipleChoiceTest)test;
         Log.d("PathProgressTest", indentation + "Dati su MultipleChoiceTest:");
         Log.d("PathProgressTest", indentation + "   Domande da mescolare: " + mcTest.shuffleQuestions);
         Log.d("PathProgressTest", indentation + "   Risposte da mescolare: " + mcTest.shuffleAnswers);
         for(int j=0; j<mcTest.questions.size(); j++) {
            Log.d("PathProgressTest", indentation + "   Domanda " + j + ":");
            Log.d("PathProgressTest", indentation + "      Testo di aiuto: " + mcTest.questions.get(j).helpText);
            Log.d("PathProgressTest", indentation + "      Domanda: " + mcTest.questions.get(j).instructions);
            Log.d("PathProgressTest", indentation + "      Risposta giusta: " + mcTest.questions.get(j).trueResponse);
            Log.d("PathProgressTest", indentation + "      Risposte errate: " + mcTest.questions.get(j).falseResponse[0] + ", " + mcTest.questions.get(j).falseResponse[1] + ", " + mcTest.questions.get(j).falseResponse[2]);
            if(mcTest.questions.get(j) instanceof MultipleChoiceImageQuiz) {
               MultipleChoiceImageQuiz mciQuiz = (MultipleChoiceImageQuiz)mcTest.questions.get(j);
               Log.d("PathProgressTest", indentation + "      Indirizzo immagine: " + mciQuiz.image);
            }
         }
      } else if(test instanceof TrueFalseTest) {
         TrueFalseTest tfTest = (TrueFalseTest)test;
         Log.d("PathProgressTest", indentation + "Dati su TrueFalseTest: ");
         Log.d("PathProgressTest", indentation + "   Domande da mescolare: " + tfTest.shuffleQuestions);
         for(int j=0; j<tfTest.questions.size(); j++) {
            Log.d("PathProgressTest", indentation + "   Domanda " + j + ":");
            Log.d("PathProgressTest", indentation + "      Testo di aiuto: " + tfTest.questions.get(j).helpText);
            Log.d("PathProgressTest", indentation + "      Domanda: " + tfTest.questions.get(j).instructions);
            Log.d("PathProgressTest", indentation + "      Risposta: " + tfTest.questions.get(j).response);
            if(tfTest.questions.get(j) instanceof TrueFalseImageQuiz) {
               TrueFalseImageQuiz mciQuiz = (TrueFalseImageQuiz)tfTest.questions.get(j);
               Log.d("PathProgressTest", indentation + "      Indirizzo immagine: " + mciQuiz.image);
            }
         }
      } else if(test instanceof BiggerShapeTest) {
         BiggerShapeTest bsTest = (BiggerShapeTest)test;
         Log.d("PathProgressTest", indentation + "Dati su BiggerShapeTest:");
         Log.d("PathProgressTest", indentation + "   Istruzioni: " + bsTest.instructions);
         Log.d("PathProgressTest", indentation + "   Ordine dei giochi da mescolare: " + bsTest.shuffleSets);
         for(int j=0; j<bsTest.games.size(); j++) {
            Log.d("PathProgressTest", indentation + "   Gioco " + j + ":");
            Log.d("PathProgressTest", indentation + "      Testo di aiuto: " + bsTest.games.get(j).helpText);
            Log.d("PathProgressTest", indentation + "      Tipo di figura geometrica: " + bsTest.games.get(j).shape);
            Log.d("PathProgressTest", indentation + "      Dimensioni della figura di sinistra: " + bsTest.games.get(j).left);
            Log.d("PathProgressTest", indentation + "      Dimensioni della figura di destra: " + bsTest.games.get(j).right);
         }
      }
   }
}
