package Data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import Data.LoggedUser;

public class LoginManager2 {
   private static LoginManager2 singleInstance;
   private LoggedUser loggedUser;
   private Context cx; //serve per poter usare SharedPreferences, la classe che permette di usare il "dizionario" con i valori di base che ci interessano

   private LoginManager2(Context cx) {
      this.cx = cx;
   }

   public static LoginManager2 sharedManager(Context cx) {
      if (singleInstance == null)
         singleInstance = new LoginManager2(cx);
      return singleInstance;
   }

   LoggedUser getLoggedUser() {
      return loggedUser;
   }

   boolean isLogged() {
      return getToken().equals(""); //Nota: il metodo chiamato in getToken() ritorna la stringa vuota se non è stato salvato alcun token
   }

   public String getToken() {
      return cx.getSharedPreferences("LogData", Context.MODE_PRIVATE).getString("token", ""); //prelevo token dal file, la stringa vuota indica cose ritornare se il token non c'è. MODE_PRIVATE è il modo standard con cui si lavora sui fogli tramite il codice, in alternativa si può usare MODE_APPEND
   }

   void login(String username, String password, AbstractDataManagerListener<Integer> listener) { //Nota: Integer è una classe wrapper

      String token = new String(), email = new String(); //per ora do per scontato di averli già ricevuto in input
      SharedPreferences sp = cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
      sp.edit().putString("token", token);
      sp.edit().putString("email", email);
      sp.edit().putString("username", username);
      sp.edit().apply();
   }

   void logout(AbstractDataManagerListener<Boolean> listener) {
      GenericLoginManagerListener urlListener= new GenericLoginManagerListener(); //forse sarà da cambiare
      urlrequest.RequestMaker.logout(cx, urlListener); //esegue la chiamata al server, qui non mi interessa se riesce o no
      //TODO: da decidere se qui dev'essere cancellato il token, il momento è giusto ma il problema è che se avviene un errore con la comunicazione perdo il token, quindi devo rifare il login. La cosa più giusta da fare sarebbe cancellarlo solo quando la chiamata ha successo, il che vorrebbe dire aggiungere l'istruzione al listener. In alternativa potrei definire un listener intermedio astratto in cui lo obbligo a fare così
   }

   void register(String email, String username, String password, AbstractDataManagerListener<Integer> listener) {

   }

   void change(String username, String password, AbstractDataManagerListener<Integer> listener) {

   }

   void check(String email, String username, String password, AbstractDataManagerListener<Integer> listener) {
      
   }
}

//Dato che ogni metodo con una richiesta da fare all'urlrequest dovrebbe scomporre il JSONObject, si potrebbe fare la scomposizione in un listener astratto intermedio, come suggerito sopra; oppure il listener potrebbe chiamare un metodo di LoginManager a cui passa il JSON

class GenericLoginManagerListener extends urlrequest.AbstractUrlRequestListener { //quando nel JSON c'è solo un valore che indica la risposta al controllo
   public void onResponse(JSONObject response) {
      try {
         int value = response.getInt("value");
      } catch (JSONException e) {

      }
   }

   public void onError(VolleyError error) {

   }
}