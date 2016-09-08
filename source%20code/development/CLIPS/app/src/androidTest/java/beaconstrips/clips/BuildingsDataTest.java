package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file BuildingsDataTest.java
 * @date 19/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU18 (Test di Unità 18). Verifica che la ricerca dei dati degli edifici più vicini all'utente sia effettuata correttamente..
 * Viene dato per scontato che i dati ci siano tutti, quindi non dovrebbero verificarsi errori.
 * I test previsti sono sei, dei primi due uno richiede un certo numero massimo di edifici e uno quelli situati entro una certa distanza; la seconda coppia di test usa le stesse coordinate ma con un valore numerico più grande, mostrando che così si ottengono più edifici; gli ultimi due test hanno delle coordinate diverse, in modo da mostrare che così cambiano gli edifici rilevati rispetto ai primi due test.
 *
 *
 * Nota: d'ora in poi per "array di n edifici" si intende un array di n oggetti, dove n viene definito da un numero preciso solo se la ricerca avviene per numero massimo di edifici, in cui la struttura dell'output previsto è la seguente:
 * "Edificio <i>"
 * "   name: <name>"
 * "   description: <description>"
 * "   otherInfos: <otherInfos>"
 * "   openingTime: <openingTime>"
 * "   address: <address>"
 * "   latitude: <latitude>"
 * "   longitude: <longitude>"
 * "   telephone: <telephone>"
 * "   email: <email>"
 * "   whatsapp: <whatsapp>"
 * "   telegram: <telegram>"
 * "   twitter: <twitter>"
 * "   facebook: <facebook>"
 * "   websiteURL: <websiteURL>"
 * [
 *    "   Percorso <j>"
 *    "      id: <id>"
 *    "      title: <title>"
 *    "      description: <description>"
 *    "      target: <target>"
 *    "      estimatedDuration: <estimatedDuration>"
 *    "      position: <position>"
 * ]
 * Legenda: i valori tra "<>" sono quelli che l'oggetto contiene, ad eccezione di "i" e "j" che invece sono dei semplici contatori, il cui valore di partenza è 0.
 *          Le parentesi quadre invece indicano che "pathsInfos" è un array dentro l'oggetto.
 *
 *
 * Stampa attesa per il test "buildingsByDistance": "Richiesta buildingsByDistance() eseguita con successo"
 *                                                                       "array di n edifici"
 * Stampa attesa per il test "buildingsByNumber": "Richiesta buildingsByNumber() eseguita con successo"
 *                                                                     "array di 1 edificio"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class BuildingsDataTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   @Test
   public void buildingsByDistance() {
      Context context = rule.getActivity().getBaseContext();
      DataRequestMaker.getBuildings(context, 46, 11, 150, true, new AbstractDataManagerListener<Building[]>() {
         public void onResponse(Building[] response) {
            Log.d("BuildingsDataTest", "Richiesta buildingsByDistance() eseguita con successo");
            for (int i=0; i <response.length ; i++) {
               Log.d("BuildingsDataTest", "Edificio " + i);
               Log.d("BuildingsDataTest", "   name: " + response[i].name);
               Log.d("BuildingsDataTest", "   description: " + response[i].description);
               Log.d("BuildingsDataTest", "   otherInfos: " + response[i].otherInfos);
               Log.d("BuildingsDataTest", "   openingTime: " + response[i].openingTime);
               Log.d("BuildingsDataTest", "   address: " + response[i].address);
               Log.d("BuildingsDataTest", "   latitude: " + response[i].latitude);
               Log.d("BuildingsDataTest", "   longitude: " + response[i].longitude);
               Log.d("BuildingsDataTest", "   telephone: " + response[i].telephone);
               Log.d("BuildingsDataTest", "   email: " + response[i].email);
               Log.d("BuildingsDataTest", "   whatsapp: " + response[i].whatsapp);
               Log.d("BuildingsDataTest", "   telegram: " + response[i].telegram);
               Log.d("BuildingsDataTest", "   twitter: " + response[i].twitter);
               Log.d("BuildingsDataTest", "   facebook: " + response[i].facebook);
               Log.d("BuildingsDataTest", "   websiteURL: " + response[i].websiteURL);
               for (int j=0; j<response[i].pathsInfos.size(); j++) {
                  Log.d("BuildingsDataTest", "   Percorso " + j);
                  Log.d("BuildingsDataTest", "      id: " + response[i].pathsInfos.get(j).id);
                  Log.d("BuildingsDataTest", "      title: " + response[i].pathsInfos.get(j).title);
                  Log.d("BuildingsDataTest", "      description: " + response[i].pathsInfos.get(j).description);
                  Log.d("BuildingsDataTest", "      target: " + response[i].pathsInfos.get(j).target);
                  Log.d("BuildingsDataTest", "      estimatedDuration: " + response[i].pathsInfos.get(j).estimatedDuration);
                  Log.d("BuildingsDataTest", "      position: " + response[i].pathsInfos.get(j).position);
               }
            }
         }
         public void onError(ServerError error) {
            Log.d("BuildingsDataTest", "Rilevato un errore in buildingsByDistance():");
            Log.d("BuildingsDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("BuildingsDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("BuildingsDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void buildingsByNumber() {
      Context context = rule.getActivity().getBaseContext();
      DataRequestMaker.getBuildings(context, 46, 11, 1, false, new AbstractDataManagerListener<Building[]>() {
         public void onResponse(Building[] response) {
            Log.d("BuildingsDataTest", "Richiesta buildingsByNumber() eseguita con successo");
            for (int i=0; i <response.length ; i++) {
               Log.d("BuildingsDataTest", "Edificio " + i);
               Log.d("BuildingsDataTest", "   name: " + response[i].name);
               Log.d("BuildingsDataTest", "   description: " + response[i].description);
               Log.d("BuildingsDataTest", "   otherInfos: " + response[i].otherInfos);
               Log.d("BuildingsDataTest", "   openingTime: " + response[i].openingTime);
               Log.d("BuildingsDataTest", "   address: " + response[i].address);
               Log.d("BuildingsDataTest", "   latitude: " + response[i].latitude);
               Log.d("BuildingsDataTest", "   longitude: " + response[i].longitude);
               Log.d("BuildingsDataTest", "   telephone: " + response[i].telephone);
               Log.d("BuildingsDataTest", "   email: " + response[i].email);
               Log.d("BuildingsDataTest", "   whatsapp: " + response[i].whatsapp);
               Log.d("BuildingsDataTest", "   telegram: " + response[i].telegram);
               Log.d("BuildingsDataTest", "   twitter: " + response[i].twitter);
               Log.d("BuildingsDataTest", "   facebook: " + response[i].facebook);
               Log.d("BuildingsDataTest", "   websiteURL: " + response[i].websiteURL);
               for (int j=0; j<response[i].pathsInfos.size(); j++) {
                  Log.d("BuildingsDataTest", "   Percorso " + j);
                  Log.d("BuildingsDataTest", "      id: " + response[i].pathsInfos.get(j).id);
                  Log.d("BuildingsDataTest", "      title: " + response[i].pathsInfos.get(j).title);
                  Log.d("BuildingsDataTest", "      description: " + response[i].pathsInfos.get(j).description);
                  Log.d("BuildingsDataTest", "      target: " + response[i].pathsInfos.get(j).target);
                  Log.d("BuildingsDataTest", "      estimatedDuration: " + response[i].pathsInfos.get(j).estimatedDuration);
                  Log.d("BuildingsDataTest", "      position: " + response[i].pathsInfos.get(j).position);
               }
            }
         }
         public void onError(ServerError error) {
            Log.d("BuildingsDataTest", "Rilevato un errore in buildingsByNumber():");
            Log.d("BuildingsDataTest", "Codice dell'errore: " + error.errorCode);
            Log.d("BuildingsDataTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("BuildingsDataTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
