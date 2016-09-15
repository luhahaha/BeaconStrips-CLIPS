package beaconstrips.clips;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import beaconstrips.clips.client.data.AppInfo;
import beaconstrips.clips.client.data.Beacon;
import beaconstrips.clips.client.data.BiggerShapeTest;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.GameCollection;
import beaconstrips.clips.client.data.LinearScoringAlgorithm;
import beaconstrips.clips.client.data.MultipleChoiceImageQuiz;
import beaconstrips.clips.client.data.MultipleChoiceTest;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.Proximity;
import beaconstrips.clips.client.data.TrueFalseImageQuiz;
import beaconstrips.clips.client.data.TrueFalseTest;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file LocalDataRequestTest.java
 * @date 26/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU27 (Test di Unità 27). Verifica che le richieste in locale di ottenere i dati degli edifici, del percorso selezionato, dei risultati salvati e delle informazioni sull'applicazione siano effettuate correttamente.
 * Tali test sono stati separati da quelli ufficiali perché richiedono che non ci sia connessione dati attiva, altrimenti i dati saranno prelevati dal server e non in locale, di conseguenza i test normali fallirebbero.
 * Una condizione fondamentale per la riuscita del test è che i dati siano effettivamente salvati nel database locale.
 *
 *
 *
 * Stampa attesa per il test "path": "Richiesta path() eseguita con successo:"
 *                                   "Dati sul percorso:"
 *                                   "ID: <pathID>"
 *                                   "Messaggio d'inizio: <startingMessage>"
 *                                   "Messaggio finale: <rewardMessage>"
 *                                   "Dati sullo step <i>: ["
 *                                   "   {Dati sul beacon di step:"
 *                                   "      ID: <id>"
 *                                   "      UUID: <UUID>"
 *                                   "      Major: <major>"
 *                                   "      Minor: <minor>"
 *                                   "   Dati sulla proximity <i>:"
 *                                   "      Dati sul beacon di proximity:"
 *                                   "         ID: <id>"
 *                                   "         UUID: <UUID>"
 *                                   "         Major: <major>"
 *                                   "         Minor: <minor>"
 *                                   "      Percentuale: <percentage>"
 *                                   "      Testo: <textToPrint>"
 *                                   "   Dati su proof:"
 *                                   "      ID: <id>"
 *                                   "      Testo iniziale: <instructions>"
 *                                   "      Titolo: <title>"
 *                                   "      Dati su algorithm:"
 *                                   "         Punteggio minimo: <minScore>"
 *                                   "         Punteggio massimo: <maxScore>"
 *                                   "         Tempo minimo: <minTime>"
 *                                   "         Tempo massimo: <maxTime>"
 *                                   "         Peso del tempo: <timeWeight>"
 *                                   "         Peso delle risposte giuste: <accuracyWeight>"
 *                                   "      Dati su test: ["
 *                                   "        {Dati su TrueFalseTest:"
 *                                   "           Domande da mescolare: <shuffleQuestions>"
 *                                   "           Domanda <i>"
 *                                   "              Testo di aiuto: <helpText>"
 *                                   "              Domanda: <instructions>"
 *                                   "              Risposta: <response>"
 *                                   "              Indirizzo immagine: <image>}"
 *                                   "        {Dati su MultipleChoiceTest:"
 *                                   "           Domande da mescolare: <shuffleQuestions>"
 *                                   "           Risposte da mescolare: <shuffleAnswers>"
 *                                   "           Domanda <i>:"
 *                                   "              Testo di aiuto: <helpText>"
 *                                   "              Domanda: <instructions>"
 *                                   "              Risposta giusta: <correctAnswer>"
 *                                   "              Risposte errate: <wrongAnswers[]>}"
 *                                   "]"
 * "Stampa attesa per il test "buildings": Richiesta buildings() eseguita con successo:"
 *                                         "["
 *                                         "Edificio <i>"
 *                                         "   name: <name>"
 *                                         "   image: <image>"
 *                                         "   description: <description>"
 *                                         "   otherInfos: <otherInfos>"
 *                                         "   openingTime: <openingTime>"
 *                                         "   address: <address>"
 *                                         "   latitude: <latitude>"
 *                                         "   longitude: <longitude>"
 *                                         "   telephone: <telephone>"
 *                                         "   email: <email>"
 *                                         "   whatsapp: <whatsapp>"
 *                                         "   telegram: <telegram>"
 *                                         "   twitter: <twitter>"
 *                                         "   facebook: <facebook>"
 *                                         "   websiteURL: <websiteURL>["
 *                                         "      Percorso <j>"
 *                                         "         id: <id>"
 *                                         "         title: <title>"
 *                                         "         description: <description>"
 *                                         "         target: <target>"
 *                                         "         estimatedDuration: <estimatedDuration>"
 *                                         "         position: <position>]"
 *                                         "]"
 * Stampa attesa per il test "pathResults": "Chiamata pathResults() eseguita con successo:"
 *                                         "["
 *                                         "   ID del percorso: <id>"
 *                                         "   Nome del percorso: <pathName>"
 *                                         "   Nome dell'edificio: <buildingName>"
 *                                         "   Orario d'inizio: <startTime>"
 *                                         "   Orario di fine: <endTime>"
 *                                         "   Punteggio finale: <totalScore>"
 *                                         "   Prova <i>:"
 *                                         "      ID della tappa: <id>"
 *                                         "      Orario d'inizio: <startTime>"
 *                                         "      Orario di fine: <endTime>"
 *                                         "      Punteggio della tappa: <score>"
 *                                         "]"
 * Stampa attesa per il test "appInfo": "Chiamata appInfo() eseguita con successo:"
 *                                      "   Descrizione: CLIPS è un progetto di Ingegneria del software, sviluppato dal team CLIPS il cui prodotto finale consisterà di un’applicazione mobile che, interagendo con dei beacons sparsi nell’area designata, guiderà l’utente attraverso un percorso. L’utente potrà completare il percorso superando tutte le prove che gli si presenteranno nelle diverse tappe. Le prove potranno essere degli indovinelli o dei semplici giochi inerenti all’area in cui si svolge il percorso."
 *                                      "   Email degli sviluppatori: beaconstrips.swe@gmail.com"
 *                                      "   Sito web: beaconstrips.tk"
 *                                      "   UUID dei beacon: f7826da6-4fa2-4e98-8024-bc5b71e0893e"
 * Legenda: I valori tra "<>" indicano che in quel posto è atteso la stampa di un dato del tipo scritto dentro.
 * Le parentesi quadre invece indicano che ci si aspetta un array di strutture uguali a quelle scritte all'interno delle parentesi stesse.
 * Viene usata questa notazione generica perché l'esito del risultato dipende da quali dati sono salvati in locale, e quindi non è possibile prevedere a priori cosa uscirà.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LocalDataRequestTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void path() {
      Context context = rule.getActivity().getBaseContext();
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo netInfo = cm.getActiveNetworkInfo();
      if (netInfo != null && netInfo.isConnectedOrConnecting()) {
         Log.d("NoServicesTest", "Connessione attiva. Impossibile eseguire il test.");
      } else {
         DataRequestMaker.getPath(context, 1, new AbstractDataManagerListener<Path>() {
            public void onResponse(Path response) {
               Log.d("PathTest", "Richiesta path() eseguita con successo");
               Log.d("NoServicesTest", "Dati sul percorso:");
               Log.d("NoServicesTest", "   ID: " + response.id);
               Log.d("NoServicesTest", "   Messaggio d'inizio: " + response.startingMessage);
               Log.d("NoServicesTest", "   Messaggio finale: " + response.rewardMessage);
               for (int i = 0; i < response.steps.size(); i++) {
                  Beacon stepBeacon = response.steps.get(i).stopBeacon;
                  Proof stepProof = response.steps.get(i).proof;
                  ArrayList<Proximity> stepProximities = response.steps.get(i).proximities;
                  Log.d("NoServicesTest", "   Dati sullo step " + i + ":");
                  Log.d("NoServicesTest", "      Dati sul beacon di step:");
                  Log.d("NoServicesTest", "         ID: " + stepBeacon.id);
                  Log.d("NoServicesTest", "         UUID: " + stepBeacon.UUID);
                  Log.d("NoServicesTest", "         Major: " + stepBeacon.major);
                  Log.d("NoServicesTest", "         Minor: " + stepBeacon.minor);
                  for (int j = 0; j < stepProximities.size(); j++) {
                     Beacon proximityBeacon = stepProximities.get(i).beacon;
                     Log.d("NoServicesTest", "      Dati sulla proximity " + j + ":");
                     Log.d("NoServicesTest", "         Dati sul beacon di proximity:");
                     Log.d("NoServicesTest", "            ID: " + proximityBeacon.id);
                     Log.d("NoServicesTest", "            UUID: " + proximityBeacon.UUID);
                     Log.d("NoServicesTest", "            Major: " + proximityBeacon.major);
                     Log.d("NoServicesTest", "            Minor: " + proximityBeacon.minor);
                     Log.d("NoServicesTest", "         Percentuale: " + stepProximities.get(i).percentage);
                     Log.d("NoServicesTest", "         Testo: " + stepProximities.get(i).textToDisplay);
                  }
                  Log.d("NoServicesTest", "      Dati su proof:");
                  Log.d("NoServicesTest", "         ID: " + stepProof.id);
                  Log.d("NoServicesTest", "         Testo iniziale: " + stepProof.instructions);
                  Log.d("NoServicesTest", "         Titolo: " + stepProof.title);
                  LinearScoringAlgorithm algorithm = stepProof.scoringAlgorithm;
                  beaconstrips.clips.client.data.Test test = stepProof.test;
                  Log.d("NoServicesTest", "         Dati su algorithm:");
                  Log.d("NoServicesTest", "            Punteggio minimo: " + algorithm.minScore);
                  Log.d("NoServicesTest", "            Punteggio massimo: " + algorithm.maxScore);
                  Log.d("NoServicesTest", "            Tempo minimo: " + algorithm.minTime);
                  Log.d("NoServicesTest", "            Tempo massimo: " + algorithm.maxTime);
                  Log.d("NoServicesTest", "            Peso del tempo: " + algorithm.timeWeight);
                  Log.d("NoServicesTest", "            Peso delle risposte giuste: " + algorithm.accuracyWeight);
                  Log.d("NoServicesTest", "         Dati su test:");
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
   }

   @Test
   public void buildings() {
      Context context = rule.getActivity().getBaseContext();
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo netInfo = cm.getActiveNetworkInfo();
      if(netInfo != null && netInfo.isConnectedOrConnecting()) {
         Log.d("NoServicesTest", "Connessione attiva. Impossibile eseguire il test.");
      } else {
         DataRequestMaker.getBuildings(context, 46, 11, 150, true, new AbstractDataManagerListener<Building[]>() {
            public void onResponse(Building[] response) {
               Log.d("NoServicesTest", "Richiesta buildings() eseguita con successo");
               for (int i=0; i <response.length ; i++) {
                  Log.d("NoServicesTest", "Edificio " + i);
                  Log.d("NoServicesTest", "   name: " + response[i].name);
                  Log.d("NoServicesTest", "   image: " + response[i].image);
                  Log.d("NoServicesTest", "   description: " + response[i].description);
                  Log.d("NoServicesTest", "   otherInfos: " + response[i].otherInfos);
                  Log.d("NoServicesTest", "   openingTime: " + response[i].openingTime);
                  Log.d("NoServicesTest", "   address: " + response[i].address);
                  Log.d("NoServicesTest", "   latitude: " + response[i].latitude);
                  Log.d("NoServicesTest", "   longitude: " + response[i].longitude);
                  Log.d("NoServicesTest", "   telephone: " + response[i].telephone);
                  Log.d("NoServicesTest", "   email: " + response[i].email);
                  Log.d("NoServicesTest", "   whatsapp: " + response[i].whatsapp);
                  Log.d("NoServicesTest", "   telegram: " + response[i].telegram);
                  Log.d("NoServicesTest", "   twitter: " + response[i].twitter);
                  Log.d("NoServicesTest", "   facebook: " + response[i].facebook);
                  Log.d("NoServicesTest", "   websiteURL: " + response[i].websiteURL);
                  for (int j=0; j<response[i].pathsInfos.size(); j++) {
                     Log.d("NoServicesTest", "   Percorso " + j);
                     Log.d("NoServicesTest", "      id: " + response[i].pathsInfos.get(j).id);
                     Log.d("NoServicesTest", "      title: " + response[i].pathsInfos.get(j).title);
                     Log.d("NoServicesTest", "      description: " + response[i].pathsInfos.get(j).description);
                     Log.d("NoServicesTest", "      target: " + response[i].pathsInfos.get(j).target);
                     Log.d("NoServicesTest", "      estimatedDuration: " + response[i].pathsInfos.get(j).estimatedDuration);
                     Log.d("NoServicesTest", "      position: " + response[i].pathsInfos.get(j).position);
                  }
               }
            }
            public void onError(ServerError error) {
               Log.d("NoServicesTest", "Rilevato un errore in buildingsByDistance():");
               Log.d("NoServicesTest", "Codice dell'errore: " + error.errorCode);
               Log.d("NoServicesTest", "Messaggio per l'utente: " + error.userMessage);
               Log.d("NoServicesTest", "Messaggio di debug: " + error.debugMessage);
            }
         });
      }
   }

   @Test
   public void pathResults() {
      Context context = rule.getActivity().getBaseContext();
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo netInfo = cm.getActiveNetworkInfo();
      if(netInfo != null && netInfo.isConnectedOrConnecting()) {
         Log.d("NoServicesTest", "Connessione attiva. Impossibile eseguire il test.");
      } else {
         android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
         android.content.SharedPreferences.Editor editor = preferences.edit();
         editor.putString("token", "cf9ad04f81bd467a817b1da8f18ba858");
         editor.apply();
         final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN);
         DataRequestMaker.getResults(context, new AbstractDataManagerListener<PathResult[]>() {
            public void onResponse(PathResult[] response) {
               Log.d("NoServicesTest", "Chiamata getResults() eseguita con successo:");
               for(int i=0; i<response.length; i++) {
                  Log.d("NoServicesTest", "   ID del percorso: " + response[i].pathID);
                  Log.d("NoServicesTest", "   Nome del percorso: " + response[i].pathName);
                  Log.d("NoServicesTest", "   Nome dell'edificio: " + response[i].buildingName);
                  Log.d("NoServicesTest", "   Orario d'inizio: " + dateFormat.format(response[i].startTime.getTime()));
                  Log.d("NoServicesTest", "   Orario di fine: " + dateFormat.format(response[i].endTime.getTime()));
                  Log.d("NoServicesTest", "   Punteggio finale: " + response[i].totalScore);
                  ArrayList<ProofResult> array = response[i].proofResults;
                  for(int j=0; j<array.size(); j++) {
                     Log.d("NoServicesTest", "   Prova " + j +":");
                     Log.d("NoServicesTest", "      ID della tappa: " + array.get(j).id);
                     Log.d("NoServicesTest", "      Orario d'inizio: " + dateFormat.format(array.get(j).startTime.getTime()));
                     Log.d("NoServicesTest", "      Orario di fine: " + dateFormat.format(array.get(j).endTime.getTime()));
                     Log.d("NoServicesTest", "      Punteggio della tappa: " + array.get(j).score);
                  }
               }
            }
            public void onError(ServerError error) {
               Log.d("NoServicesTest", "Rilevato un errore in getResults():");
               Log.d("NoServicesTest", "Codice dell'errore: " + error.errorCode);
               Log.d("NoServicesTest", "Messaggio per l'utente: " + error.userMessage);
               Log.d("NoServicesTest", "Messaggio di debug: " + error.debugMessage);
            }
         });
      }
   }

   @Test
   public void appInfo() {
      Context context = rule.getActivity().getBaseContext();
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo netInfo = cm.getActiveNetworkInfo();
      if(netInfo != null && netInfo.isConnectedOrConnecting()) {
         Log.d("NoServicesTest", "Connessione attiva. Impossibile eseguire il test.");
      } else {
         DataRequestMaker.getAppInfo(context, new AbstractDataManagerListener<AppInfo>() {
            public void onResponse(AppInfo response) {
               Log.d("AppInfoDataTest", "Richiesta appInfo() eseguita con successo:");
               Log.d("AppInfoDataTest", "   Descrizione: " + response.description);
               Log.d("AppInfoDataTest", "   Email degli sviluppatori: " + response.supportEmail);
               Log.d("AppInfoDataTest", "   Sito web: " + response.website);
               Log.d("AppInfoDataTest", "   UUID dei beacon: " + response.UUID);
            }
            public void onError(ServerError error) {
               Log.d("NoServicesTest", "Rilevato un errore in getResults():");
               Log.d("NoServicesTest", "Codice dell'errore: " + error.errorCode);
               Log.d("NoServicesTest", "Messaggio per l'utente: " + error.userMessage);
               Log.d("NoServicesTest", "Messaggio di debug: " + error.debugMessage);
            }
         });
      }
   }

   public void printTest(beaconstrips.clips.client.data.Test test, int indLevel) { //metodo di supporto, indLevel indica il livello di indentazione, ogni unità corrisponde a 3 spazi
      String indentation = "";
      for(int i=0; i<indLevel; i++) {
         indentation += "   ";
      }
      if(test instanceof GameCollection) {
         GameCollection gameCollection = (GameCollection)test;
         Log.d("NoServicesTest", indentation + "Dati su GameCollection:");
         Log.d("NoServicesTest", indentation + "   Test da mescolare: " + gameCollection.shuffleGames);
         for(int j=0; j<gameCollection.games.size(); j++) {
            printTest(gameCollection.games.get(j), indLevel+1);
         }
      } else if(test instanceof MultipleChoiceTest) {
         MultipleChoiceTest mcTest = (MultipleChoiceTest)test;
         Log.d("NoServicesTest", indentation + "Dati su MultipleChoiceTest:");
         Log.d("NoServicesTest", indentation + "   Domande da mescolare: " + mcTest.shuffleQuestions);
         Log.d("NoServicesTest", indentation + "   Risposte da mescolare: " + mcTest.shuffleAnswers);
         for(int j=0; j<mcTest.questions.size(); j++) {
            Log.d("NoServicesTest", indentation + "   Domanda " + j + ":");
            Log.d("NoServicesTest", indentation + "      Testo di aiuto: " + mcTest.questions.get(j).helpText);
            Log.d("NoServicesTest", indentation + "      Domanda: " + mcTest.questions.get(j).instructions);
            Log.d("NoServicesTest", indentation + "      Risposta giusta: " + mcTest.questions.get(j).trueResponse);
            Log.d("NoServicesTest", indentation + "      Risposte errate: " + mcTest.questions.get(j).falseResponse[0] + ", " + mcTest.questions.get(j).falseResponse[1] + ", " + mcTest.questions.get(j).falseResponse[2]);
            if(mcTest.questions.get(j) instanceof MultipleChoiceImageQuiz) {
               MultipleChoiceImageQuiz mciQuiz = (MultipleChoiceImageQuiz)mcTest.questions.get(j);
               Log.d("NoServicesTest", indentation + "      Indirizzo immagine: " + mciQuiz.image);
            }
         }
      } else if(test instanceof TrueFalseTest) {
         TrueFalseTest tfTest = (TrueFalseTest)test;
         Log.d("NoServicesTest", indentation + "Dati su TrueFalseTest: ");
         Log.d("NoServicesTest", indentation + "   Domande da mescolare: " + tfTest.shuffleQuestions);
         for(int j=0; j<tfTest.questions.size(); j++) {
            Log.d("NoServicesTest", indentation + "   Domanda " + j + ":");
            Log.d("NoServicesTest", indentation + "      Testo di aiuto: " + tfTest.questions.get(j).helpText);
            Log.d("NoServicesTest", indentation + "      Domanda: " + tfTest.questions.get(j).instructions);
            Log.d("NoServicesTest", indentation + "      Risposta: " + tfTest.questions.get(j).response);
            if(tfTest.questions.get(j) instanceof TrueFalseImageQuiz) {
               TrueFalseImageQuiz mciQuiz = (TrueFalseImageQuiz)tfTest.questions.get(j);
               Log.d("NoServicesTest", indentation + "      Indirizzo immagine: " + mciQuiz.image);
            }
         }
      } else if(test instanceof BiggerShapeTest) {
         BiggerShapeTest bsTest = (BiggerShapeTest)test;
         Log.d("NoServicesTest", indentation + "Dati su BiggerShapeTest:");
         Log.d("NoServicesTest", indentation + "   Istruzioni: " + bsTest.instructions);
         Log.d("NoServicesTest", indentation + "   Ordine dei giochi da mescolare: " + bsTest.shuffleSets);
         for(int j=0; j<bsTest.games.size(); j++) {
            Log.d("NoServicesTest", indentation + "   Gioco " + j + ":");
            Log.d("NoServicesTest", indentation + "      Testo di aiuto: " + bsTest.games.get(j).helpText);
            Log.d("NoServicesTest", indentation + "      Tipo di figura geometrica: " + bsTest.games.get(j).shape);
            Log.d("NoServicesTest", indentation + "      Dimensioni della figura di sinistra: " + bsTest.games.get(j).left);
            Log.d("NoServicesTest", indentation + "      Dimensioni della figura di destra: " + bsTest.games.get(j).right);
         }
      }
   }
}
