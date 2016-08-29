package beaconstrips.clips;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file RegistrationTest.java
 * @date 23/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU6 (Test di Unità 6). Verifica che la richiesta al server di registrare il profilo dell'utente sia effettuata correttamente.
 * Oltre al test con i dati corretti ne verranno effettuati altri 3, in cui verrà testa la richiesta di registrazione con un dato errato, che saranno rispettivamente l'email, lo username e la password.
 * I casi con errore testati saranno la registrazione con un'email errata, con un'email già esistente, con uno username già esistente e infine con una password di formato errato.
 *
 *
 * Stampa attesa per il test "registration": "Chiamata registration() eseguita con successo"
 * Stampa attesa per il test "registrationWrongEmail": "Rilevato un errore in registrationWrongEmail() con email con formato errato:"
 *                                                     "Codice dell'errore: 460"
 *                                                     "Messaggio per l'utente: usa un indirizzo email valido"
 *                                                     "Messaggio di debug: email is not valid"
 *                                                     "Rilevato un errore in registrationWrongEmail() con email già esistente:"
 *                                                     "Codice dell'errore: 460"
 *                                                     "Messaggio per l'utente: "
 *                                                     "Messaggio di debug: "
 * Stampa attesa per il test "registrationWrongUsername": "Rilevato un errore in registrationWrongUsername() con username già usato:"
 *                                                     "Codice dell'errore: 461"
 *                                                     "Messaggio per l'utente: "
 *                                                     "Messaggio di debug: "
 * Stampa attesa per il test "registrationWrongPassword": "Rilevato un errore in registrationWrongPassword() con password con formato sbagliato:"
 *                                                     "Codice dell'errore: 460"
 *                                                     "Messaggio per l'utente: La password deve contenere almeno 6 caratteri e al massimo 16."
 *                                                     "Sono ammesse le lettere latine senza accenti (maiuscole e minuscole), i numeri e i segni ! @ # $ & ( ) - / \ _ ?"
 *                                                     "Messaggio di debug: password is not valid"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RegistrationTest {
   @Rule
   public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

   /*@Test
   public void registration() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.registration(context, "prova34@gmail.com", "Prova34", "prova34", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("RegistrationTest", "Chiamata registration() eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("RegistrationTest", "Rilevato un errore in registration():");
            Log.d("RegistrationTest", "Codice dell'errore: " + error.errorCode);
            Log.d("RegistrationTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("RegistrationTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }*/

   @Test
   public void registrationWrongEmail() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.registration(context, "prova34gmail.com", "Prova34", "prova34", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("RegistrationTest", "Chiamata registrationWrongEmail() con email con formato errato eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("RegistrationTest", "Rilevato un errore in registrationWrongEmail() con email con formato errato:");
            Log.d("RegistrationTest", "Codice dell'errore: " + error.errorCode);
            Log.d("RegistrationTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("RegistrationTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
      RequestMaker.registration(context, "prova@gmail.com", "Prova34", "prova34", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("RegistrationTest", "Chiamata registrationWrongEmail() con email già esistente eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("RegistrationTest", "Rilevato un errore in registrationWrongEmail() con email già esistente:");
            Log.d("RegistrationTest", "Codice dell'errore: " + error.errorCode);
            Log.d("RegistrationTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("RegistrationTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void registrationWrongUsername() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.registration(context, "prova34@gmail.com", "Cenze", "prova34", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("RegistrationTest", "Chiamata registrationWrongUsername() con username già usato eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("RegistrationTest", "Rilevato un errore in registrationWrongUsername() con username già usato:");
            Log.d("RegistrationTest", "Codice dell'errore: " + error.errorCode);
            Log.d("RegistrationTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("RegistrationTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }

   @Test
   public void registrationWrongPassword() {
      Context context = rule.getActivity().getBaseContext();
      RequestMaker.registration(context, "prova34@gmail.com", "Prova34", "prova", new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) {
            Log.d("RegistrationTest", "Chiamata registrationWrongPassword() con password con formato sbagliato eseguita con successo");
         }
         public void onError(ServerError error) {
            Log.d("RegistrationTest", "Rilevato un errore in registrationWrongPassword() con password con formato sbagliato:");
            Log.d("RegistrationTest", "Codice dell'errore: " + error.errorCode);
            Log.d("RegistrationTest", "Messaggio per l'utente: " + error.userMessage);
            Log.d("RegistrationTest", "Messaggio di debug: " + error.debugMessage);
         }
      });
   }
}
