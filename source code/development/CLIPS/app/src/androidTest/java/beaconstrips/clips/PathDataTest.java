package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import beaconstrips.clips.client.data.Beacon;
import beaconstrips.clips.client.data.BiggerShapeTest;
import beaconstrips.clips.client.data.GameCollection;
import beaconstrips.clips.client.data.LinearScoringAlgorithm;
import beaconstrips.clips.client.data.MultipleChoiceImageQuiz;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.MultipleChoiceTextQuiz;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.Proximity;
import beaconstrips.clips.client.data.TrueFalseImageQuiz;
import beaconstrips.clips.client.data.TrueFalseTest;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file PathDataTest.java
 * @date 23/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU16 (Test di Unità 16). Verifica che la richiesta di ottenere i dati del percorso selezionato sia effettuata correttamente.
 *
 * Stampa attesa per il test "path": "Richiesta path() eseguita con successo:"
 *                                   "Dati sul percorso:"
 *                                   "   ID: 1"
 *                                   "   Messaggio d'inizio: Buongiorno sviluppatore, prova questo percorso se funziona"
 *                                   "   Messaggio finale: Complimenti funziona!"
 *                                   "   Dati sullo step 0:"
 *                                   "      Dati sul beacon di step:"
 *                                   "         ID: 1"
 *                                   "         UUID: 12345678912345678912345678912345"
 *                                   "         Major: 22"
 *                                   "         Minor: 11"
 *                                   "      Dati sulla proximity 0:"
 *                                   "         Dati sul beacon di proximity:"
 *                                   "            ID: 2"
 *                                   "            UUID: 12345678912345678912344"
 *                                   "            Major: 33"
 *                                   "            Minor: 22"
 *                                   "         Percentuale: 80.0"
 *                                   "         Testo: fuoco"
 *                                   "      Dati su proof:"
 *                                   "         ID: 4"
 *                                   "         Testo iniziale: Rispondi alla domanda"
 *                                   "         Titolo: Domanda delle scale"
 *                                   "         Dati su algorithm:"
 *                                   "            Punteggio minimo: 0"
 *                                   "            Punteggio massimo: 100"
 *                                   "            Tempo minimo: 10.0"
 *                                   "            Tempo massimo: 60.0"
 *                                   "            Peso del tempo: 1.0"
 *                                   "            Peso delle risposte giuste: 1.0"
 *                                   "         Dati su test:"
 *                                   "            Dati su GameCollection:"
 *                                   "               Test da mescolare: true"
 *                                   "               Dati su TrueFalseTest:"
 *                                   "                  Domande da mescolare: false"
 *                                   "                  Domanda 0:"
 *                                   "                     Testo di aiuto: ...mmm..."
 *                                   "                     Domanda: Napoleone Bonaparte era italiano"
 *                                   "                     Risposta: true"
 *                                   "                  Domanda 1:"
 *                                   "                     Testo di aiuto: Eccerto!"
 *                                   "                     Domanda: Matteo Franco usa MS World"
 *                                   "                     Risposta: true"
 *                                   "                  Domanda 2:"
 *                                   "                     Testo di aiuto: Se va là..."
 *                                   "                     Domanda: Luca è un bravo programmatore"
 *                                   "                     Risposta: false"
 *                                   "                     Indirizzo immagine: http://beaconstrips.tk/media/luca.jpg"
 *                                   "   Dati sullo step 1:"
 *                                   "      Dati sul beacon di step:"
 *                                   "         ID: 2"
 *                                   "         UUID: 12345678912345678912344"
 *                                   "         Major: 33"
 *                                   "         Minor: 22"
 *                                   "      Dati su proof:"
 *                                   "         ID: 5"
 *                                   "         Testo iniziale: Rispondi alla domanda"
 *                                   "         Titolo: Domanda della lavagna"
 *                                   "         Dati su algorithm:"
 *                                   "            Punteggio minimo: 0"
 *                                   "            Punteggio massimo: 100"
 *                                   "            Tempo minimo: 0.0"
 *                                   "            Tempo massimo: 0.0"
 *                                   "            Peso del tempo: 0.0"
 *                                   "            Peso delle risposte giuste: 1.0"
 *                                   "         Dati su test:"
 *                                   "            Dati su MultipleChoiceTest:"
 *                                   "               Domande da mescolare: false"
 *                                   "               Risposte da mescolare: true"
 *                                   "               Domanda 0:"
 *                                   "                  Testo di aiuto: Non mi pare fosse bianco!"
 *                                   "                  Domanda: Di che colore era il cavallo bianco di Napoleone?"
 *                                   "                  Risposta giusta: Bianco"
 *                                   "                  Risposte errate: Rosa, Blu, Rosso"
 *                                   "               Domanda 1:"
 *                                   "                  Testo di aiuto: 5 + 5 = 55"
 *                                   "                  Domanda: 5 + 5 = "
 *                                   "                  Risposta giusta: 10"
 *                                   "                  Risposte errate: 9, 11, 12"
 *                                   "               Domanda 2:"
 *                                   "                  Testo di aiuto: i pare fosse Amici di Maria, no?"
 *                                   "                  Domanda: Qual è il programma preferito di Matteo Franco?"
 *                                   "                  Risposta giusta: MS World"
 *                                   "                  Risposte errate: StarUML, Android Studio, Paint"
 *                                   "               Domanda 3:"
 *                                   "                  Testo di aiuto: Non farti ingannare dagli occhi a mandorla…"
 *                                   "                  Domanda: Dove è nato Napoleone Bonaparte?"
 *                                   "                  Risposta giusta: Italia"
 *                                   "                  Risposte errate: Giappone, Turchia, Francia"
 *                                   "               Domanda 4:"
 *                                   "                  Testo di aiuto: Io pagherei ben di più!"
 *                                   "                  Domanda: Quanto saresti disposto a pagare per la fantastica app che stai usando?"
 *                                   "                  Risposta giusta: Non la installo neanche se è gratis"
 *                                   "                  Risposte errate: 0.99€, 1.99€, 0"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class PathDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void path() {
      Context context = rule.getActivity().getBaseContext();
      DataRequestMaker.getPath(context, 1, new AbstractDataManagerListener<Path>() {
         public void onResponse(Path response) {
            Log.d("PathDataTest", "Richiesta path() eseguita con successo");
            Log.d("PathDataTest", "Dati sul percorso:");
            Log.d("PathDataTest", "   ID: " + response.id);
            Log.d("PathDataTest", "   Messaggio d'inizio: " + response.startingMessage);
            Log.d("PathDataTest", "   Messaggio finale: " + response.rewardMessage);
            for(int i=0; i<response.steps.size(); i++) {
               Beacon stepBeacon = response.steps.get(i).stopBeacon;
               Proof stepProof = response.steps.get(i).proof;
               ArrayList<Proximity> stepProximities = response.steps.get(i).proximities;
               Log.d("PathDataTest", "   Dati sullo step " + i + ":");
               Log.d("PathDataTest", "      Dati sul beacon di step:");
               Log.d("PathDataTest", "         ID: " + stepBeacon.id);
               Log.d("PathDataTest", "         UUID: " + stepBeacon.UUID);
               Log.d("PathDataTest", "         Major: " + stepBeacon.major);
               Log.d("PathDataTest", "         Minor: " + stepBeacon.minor);
               for(int j=0; j<stepProximities.size(); j++) {
                  Beacon proximityBeacon = stepProximities.get(j).beacon;
                  Log.d("PathDataTest", "      Dati sulla proximity " +j +":");
                  Log.d("PathDataTest", "         Dati sul beacon di proximity:");
                  Log.d("PathDataTest", "            ID: " + proximityBeacon.id);
                  Log.d("PathDataTest", "            UUID: " + proximityBeacon.UUID);
                  Log.d("PathDataTest", "            Major: " + proximityBeacon.major);
                  Log.d("PathDataTest", "            Minor: " + proximityBeacon.minor);
                  Log.d("PathDataTest", "         Percentuale: " + stepProximities.get(i).percentage);
                  Log.d("PathDataTest", "         Testo: " + stepProximities.get(i).textToDisplay);
               }
               Log.d("PathDataTest", "      Dati su proof:");
               Log.d("PathDataTest", "         ID: " + stepProof.id);
               Log.d("PathDataTest", "         Testo iniziale: " + stepProof.instructions);
               Log.d("PathDataTest", "         Titolo: " + stepProof.title);
               LinearScoringAlgorithm algorithm = stepProof.scoringAlgorithm;
               beaconstrips.clips.client.data.Test test = stepProof.test;
               Log.d("PathDataTest", "         Dati su algorithm:");
               Log.d("PathDataTest", "            Punteggio minimo: " + algorithm.minScore);
               Log.d("PathDataTest", "            Punteggio massimo: " + algorithm.maxScore);
               Log.d("PathDataTest", "            Tempo minimo: " + algorithm.minTime);
               Log.d("PathDataTest", "            Tempo massimo: " + algorithm.maxTime);
               Log.d("PathDataTest", "            Peso del tempo: " + algorithm.timeWeight);
               Log.d("PathDataTest", "            Peso delle risposte giuste: " + algorithm.accuracyWeight);
               Log.d("PathDataTest", "         Dati su test:");
               printTest(test, 4);
            }
         }
         public void onError(ServerError error) {
            Log.d("PathTest", "Rilevato un errore in path():");
            Log.d("PathTest", "Codice dell'errore: " + error.errorCode);
            Log.d("PathTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("PathTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   public void printTest(beaconstrips.clips.client.data.Test test, int indLevel) { //metodo di supporto, indLevel indica il livello di indentazione, ogni unità corrisponde a 3 spazi
      String indentation = "";
      for(int i=0; i<indLevel; i++) {
         indentation += "   ";
      }
      if(test instanceof GameCollection) {
         GameCollection gameCollection = (GameCollection)test;
         Log.d("PathDataTest", indentation + "Dati su GameCollection:");
         Log.d("PathDataTest", indentation + "   Test da mescolare: " + gameCollection.shuffleGames);
         for(int j=0; j<gameCollection.games.size(); j++) {
            printTest(gameCollection.games.get(j), indLevel+1);
         }
      } else if(test instanceof MultipleChoiceTest) {
         MultipleChoiceTest mcTest = (MultipleChoiceTest)test;
         Log.d("PathDataTest", indentation + "Dati su MultipleChoiceTest:");
         Log.d("PathDataTest", indentation + "   Domande da mescolare: " + mcTest.shuffleQuestions);
         Log.d("PathDataTest", indentation + "   Risposte da mescolare: " + mcTest.shuffleAnswers);
         for(int j=0; j<mcTest.questions.size(); j++) {
            Log.d("PathDataTest", indentation + "   Domanda " + j + ":");
            Log.d("PathDataTest", indentation + "      Testo di aiuto: " + mcTest.questions.get(j).helpText);
            Log.d("PathDataTest", indentation + "      Domanda: " + mcTest.questions.get(j).instructions);
            Log.d("PathDataTest", indentation + "      Risposta giusta: " + mcTest.questions.get(j).trueResponse);
            Log.d("PathDataTest", indentation + "      Risposte errate: " + mcTest.questions.get(j).falseResponse[0] + ", " + mcTest.questions.get(j).falseResponse[1] + ", " + mcTest.questions.get(j).falseResponse[2]);
            if(mcTest.questions.get(j) instanceof MultipleChoiceImageQuiz) {
               MultipleChoiceImageQuiz mciQuiz = (MultipleChoiceImageQuiz)mcTest.questions.get(j);
               Log.d("PathDataTest", indentation + "      Indirizzo immagine: " + mciQuiz.image);
            }
         }
      } else if(test instanceof TrueFalseTest) {
         TrueFalseTest tfTest = (TrueFalseTest)test;
         Log.d("PathDataTest", indentation + "Dati su TrueFalseTest: ");
         Log.d("PathDataTest", indentation + "   Domande da mescolare: " + tfTest.shuffleQuestions);
         for(int j=0; j<tfTest.questions.size(); j++) {
            Log.d("PathDataTest", indentation + "   Domanda " + j + ":");
            Log.d("PathDataTest", indentation + "      Testo di aiuto: " + tfTest.questions.get(j).helpText);
            Log.d("PathDataTest", indentation + "      Domanda: " + tfTest.questions.get(j).instructions);
            Log.d("PathDataTest", indentation + "      Risposta: " + tfTest.questions.get(j).response);
            if(tfTest.questions.get(j) instanceof TrueFalseImageQuiz) {
               TrueFalseImageQuiz mciQuiz = (TrueFalseImageQuiz)tfTest.questions.get(j);
               Log.d("PathDataTest", indentation + "      Indirizzo immagine: " + mciQuiz.image);
            }
         }
      } else if(test instanceof BiggerShapeTest) {
         BiggerShapeTest bsTest = (BiggerShapeTest)test;
         Log.d("PathDataTest", indentation + "Dati su BiggerShapeTest:");
         Log.d("PathDataTest", indentation + "   Istruzioni: " + bsTest.instructions);
         Log.d("PathDataTest", indentation + "   Ordine dei giochi da mescolare: " + bsTest.shuffleSets);
         for(int j=0; j<bsTest.games.size(); j++) {
            Log.d("PathDataTest", indentation + "   Gioco " + j + ":");
            Log.d("PathDataTest", indentation + "      Testo di aiuto: " + bsTest.games.get(j).helpText);
            Log.d("PathDataTest", indentation + "      Tipo di figura geometrica: " + bsTest.games.get(j).shape);
            Log.d("PathDataTest", indentation + "      Dimensioni della figura di sinistra: " + bsTest.games.get(j).left);
            Log.d("PathDataTest", indentation + "      Dimensioni della figura di destra: " + bsTest.games.get(j).right);
         }
      }
   }
}
