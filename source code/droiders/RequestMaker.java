package urlrequest;

import android.content.Context;

/**
 * Created by andrea on 01/07/16.
 */

//questa classe contiene i metodi per effettuare tutti i tipi di richieste al server richiesti all'interno dell'app
public class RequestMaker {
    
    
    
    //effettua la chiamata per ottenere l'UUID dei beacon e le stringhe con le info dell'app
    public static void getAppInfo(Context cx) {
        AppInfoRequest request = new AppInfoRequest(cx); //ULRequest viene automaticamente inizializzato
    }

    //l'userID viene ricavato dal LoginManager, potrebbe essere anche tolto perchè si può ricavare dal token inviato visto che l'operazione prevede che l'utente debba essere autenticato
    public static void getResults(Context cx) {
        GetResultsRequest request = new GetResultsRequest(cx);
    }


    public static void saveResult(Context cx, Data.PathResult result, URLRequestListener listener) { //PathResult non è ancora definito
      SaveResultRequest request = new SaveResultRequest(cx, result, listener);
    }

    //pathID non sarebbe meglio se fosse una String visto che altrimenti non può cominciare con 0?
    public static void getPath(Context cx, int pathID) {
        PathRequest request = new PathRequest(cx, pathID);
    }
    public static void getBuildings(Context cx, double latitude, double longitude) {
        BuildingsRequest request = new BuildingsRequest(cx, latitude, longitude); //nei diagrammi c'è discordanza sulla presenza o meno del numero massimo di edifici
    }

    public static void register(Context cx, String email, String username, String password) {
        RegistrationRequest request = new RegistrationRequest(cx, email,username,password);
    }
    public static void login(Context cx, String email, String password) {
        LoginRequest request = new LoginRequest(cx, email,password);
    }

    //secondo il nostro diagramma dovrebbe esserci una classe User al posto di email, in realtà non è richiesto alcun dato aggiuntivo perché viene tutto ricavato dal token
    public static void logout(Context cx) {
        LogoutRequest request = new LogoutRequest(cx);
    }
}
