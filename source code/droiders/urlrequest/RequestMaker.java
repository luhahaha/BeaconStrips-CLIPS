package urlrequest;

import android.content.Context;

/**
 * Created by andrea on 01/07/16.
 */

//questa classe contiene i metodi per effettuare tutti i tipi di richieste al server richiesti all'interno dell'app
public class RequestMaker {


   //effettua la chiamata per ottenere l'UUID dei beacon e le stringhe con le info dell'app
   public static void getAppInfo(Context cx, AbstractUrlRequestListener listener) {
      AppInfoRequest request = new AppInfoRequest(cx, listener); //ULRequest viene automaticamente inizializzato
   }

   //effettua la chiamata per ottenere i risultati dei percorsi dell'utente
   public static void getResults(Context cx, AbstractUrlRequestListener listener) {
      GetResultsRequest request = new GetResultsRequest(cx, listener);
   }

   //effettua la chiamata per salvare il risultato appena ottenuto dall'utente
   public static void saveResult(Context cx, Data.PathResult result, AbstractUrlRequestListener listener) {
      SaveResultRequest request = new SaveResultRequest(cx, result, listener);
   }

   //effettua la chiamata per ottenere tutti i dati relativi al percorso selezionato
   public static void getPath(Context cx, int pathID, AbstractUrlRequestListener listener) {
      PathRequest request = new PathRequest(cx, pathID, listener);
   }

   //effettua la chiamata per ottenere i dati degli edifici nei dintorni
   public static void getBuildings(Context cx, double latitude, double longitude, AbstractUrlRequestListener listener) {
      BuildingsRequest request = new BuildingsRequest(cx, latitude, longitude, listener);
   }

   //effettua la chiamata per verificare se i dati inseriti nella registrazione sono validi
   public static void register(Context cx, String email, String username, String password, AbstractUrlRequestListener listener) {
      RegistrationRequest request = new RegistrationRequest(cx, email, username, password, listener);
   }

   //effettua la chiamata per verificare sei dati di login sono corretti
   public static void login(Context cx, String email, String password, AbstractUrlRequestListener listener) {
      LoginRequest request = new LoginRequest(cx, email, password, listener);
   }

   //effettua la chiamata per informare il server che l'utente ha fatto il logout, quindi può cancellare il relativo token
   public static void logout(Context cx, AbstractUrlRequestListener listener) {
      LogoutRequest request = new LogoutRequest(cx, listener);
   }

   //effettua la chiamata per cambiare lo username e/o la password del profilo dell'utente
   public static void changeProfileData(Context cx, String username, String password, AbstractUrlRequestListener listener) {
      ChangeProfileDataRequest request=new ChangeProfileDataRequest(cx, username, password, listener);
   }

   //effettua la chiamata per controllare se i dati del profilo sono corretti, in modo da poter avvisare l'utente durante la compilazione dei dati per la registrazione
   public static void check(Context cx, String email, String username, String password, AbstractUrlRequestListener listener) {
      CheckRequest request = new CheckRequest(cx, email, username, password, listener);
   }
}
