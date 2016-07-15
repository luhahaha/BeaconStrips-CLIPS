package Data.datamanager;

import android.content.Context;

/**
 * Created by andrea on 08/07/16.
 */

//QUESTA È UNA CLASSE DI PROVA PER I TEST, INFATTI NON USA ULTERIORI CLASSI E IL TOKEN VIENE SALVATO A MANO
public class LoginManager {
   private static LoginManager singleInstance;
   private String token; //aggiungere il token stabilito
   private Context cx; //non servirebbe ma così non ci da fastidio quando implementiamo la classe vera

   private LoginManager(Context cx) {
      this.cx=cx;
      token = "";
   }

   public static LoginManager sharedManager(Context cx) {
      if (singleInstance == null)
         singleInstance = new LoginManager(cx);
      return singleInstance;
   }

   public String getToken() {
      return token;
   }
}