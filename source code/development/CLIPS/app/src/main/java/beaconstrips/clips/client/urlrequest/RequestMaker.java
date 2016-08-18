package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import beaconstrips.clips.client.data.PathResult;

/**
 * @file RequestMaker.java
 * @date 01/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che funziona da interfaccia tra l'urlrequest e l'applicazione tramite i metodi statici definiti nella classe
 */

public class RequestMaker {
   //effettua la chiamata per ottenere l'UUID dei beacon e le stringhe con le info dell'app
   public static void getAppInfo(Context cx, AbstractUrlRequestListener listener) {
      AppInfoRequest request = new AppInfoRequest(cx, listener);
   }

   //effettua la chiamata per ottenere i risultati dei percorsi dell'utente
   public static void getResults(Context cx, AbstractUrlRequestListener listener) {
      GetResultsRequest request = new GetResultsRequest(cx, listener);
   }

   //effettua la chiamata per salvare il risultato appena ottenuto dall'utente
   public static void saveResult(Context cx, PathResult result, AbstractUrlRequestListener listener) {
      SaveResultRequest request = new SaveResultRequest(cx, result, listener);
   }

   //effettua la chiamata per ottenere tutti i dati relativi al percorso selezionato
   public static void getPath(Context cx, int pathID, AbstractUrlRequestListener listener) {
      PathRequest request = new PathRequest(cx, pathID, listener);
   }

   //effettua la chiamata per ottenere i dati degli edifici nei dintorni, searchByDistance è true se la ricerca deve restituire tutti gli edifici entro i maxNumber chilometri, è false se invece deve ritornare i maxNumber edifici più vicini
   public static void getBuildings(Context cx, double latitude, double longitude, int maxNumber, boolean searchByDistance, AbstractUrlRequestListener listener) {
      BuildingsRequest request = new BuildingsRequest(cx, latitude, longitude, maxNumber, searchByDistance, listener);
   }

   //effettua la chiamata per verificare se i dati inseriti nella registrazione sono validi
   public static void registration(Context cx, String email, String username, String password, AbstractUrlRequestListener listener) {
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
   public static void changeProfileData(Context cx, String username, String oldPassword, String password, AbstractUrlRequestListener listener) {
      ChangeProfileDataRequest request=new ChangeProfileDataRequest(cx, username, oldPassword, password, listener);
   }

   //effettua la chiamata per controllare se i dati del profilo sono corretti, in modo da poter avvisare l'utente durante la compilazione dei dati per la registrazione
   public static void check(Context cx, String email, String username, String password, AbstractUrlRequestListener listener) {
      CheckRequest request = new CheckRequest(cx, email, username, password, listener);
   }

   //effettua la chiamata per ottenere i dati del profilo dell'utente attualmente salvati
   public static void getProfileData(Context cx, AbstractUrlRequestListener listener) {
      GetProfileDataRequest request = new GetProfileDataRequest(cx, listener);
   }

   //effettua la chiamata per ottenere la classifica del percorso selezionato
   public static void getRanking(Context cx, int pathID, AbstractUrlRequestListener listener) {
      GetRankingRequest request = new GetRankingRequest(cx, pathID, listener);
   }
}
