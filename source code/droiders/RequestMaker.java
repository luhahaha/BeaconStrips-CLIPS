package urlrequest;

/**
 * Created by andrea on 01/07/16.
 */

//questa classe contiene i metodi per effettuare tutti i tipi di richieste al server richiesti all'interno dell'app
public class RequestMaker {

    //effettua la chiamata per ottenere l'UUID dei beacon e le stringhe con le info dell'app
    public static void getAppInfo() {
        AppInfoRequest request = new AppInfoRequest(); //ULRequest viene automaticamente inizializzato
    }

    //l'userID viene ricavato dal LoginManager, potrebbe essere anche tolto perchè si può ricavare dal token inviato visto che l'operazione prevede che l'utente debba essere autenticato
    public static void getResults() {
        GetResultsRequest request = new GetResultsRequest();
    }


    //public static void saveResult(PathResult result, URLRequestListener listener) { //PathResult non è ancora definito
    //  SaveResultRequest request = new SaveResultRequest(result, listener);
    //}

    //pathID non sarebbe meglio se fosse una String visto che altrimenti non può cominciare con 0?
    public static void getPath(int pathID) {
        PathRequest request = new PathRequest(pathID);
    }
    public static void getBuildings(double latitude, double longitude) {
        BuildingsRequest request = new BuildingsRequest(latitude, longitude); //nei diagrammi c'è discordanza sulla presenza o meno del numero massimo di edifici
    }

    public static void register(String email, String username, String password) {
        RegistrationRequest request = new RegistrationRequest(email,username,password);
    }
    public static void login(String email, String password) {
        LoginRequest request = new LoginRequest(email,password);
    }

    //secondo il nostro diagramma dovrebbe esserci una classe User al posto di email, che però non esiste, da chiedere se per caso è meglio usare un altro dato
    public static void logout() {
        LogoutRequest request = new LogoutRequest();
    }
}
