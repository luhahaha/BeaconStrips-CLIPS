package Data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;

import Data.LoggedUser;

public class LoginManager2 {
   private static LoginManager2 singleInstance;
   private LoggedUser loggedUser;
   private Context cx; //serve per poter usare SharedPreferences, la classe che permette di usare il "dizionario" con i valori di base che ci interessano

   private LoginManager2(Context cx) {
      this.cx=cx;
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
      return cx.getSharedPreferences("LogData", Context.MODE_PRIVATE).getString("token", ""); //prelevo token dal file
   }

   void login(String username, String password, urlrequest.AbstractListener listener) {
      String token=new String(); //per ora do per scontato di averlo già ricevuto in input
      SharedPreferences sp=cx.getSharedPreferences("LogData", Context.MODE_PRIVATE);
      sp.edit().putString("token", token);
      sp.edit().apply();
   }

   void logout(urlrequest.AbstractListener listener) {

   }

   void register(String email, String username, String password, urlrequest.AbstractListener listener) {

   }

   void change(String username, String password, urlrequest.AbstractListener listener) {

   }
}