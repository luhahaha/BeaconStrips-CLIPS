package urlrequest;

/**
 * Created by andrea on 01/07/16.
 */

//questa classe contiene i metodi per effettuare tutti i tipi di richieste al server richiesti all'interno dell'app
public class RequestMaker {

    //effettua la chiamata per ottenere l'UUID dei beacon e le stringhe con le info dell'app
    public static void getAppInfo(AbstractListener listener) {
        AppInfoRequest request = new AppInfoRequest(listener); //ULRequest viene automaticamente inizializzato
    }
    public static void getResults(AbstractListener listener) { //l'userID viene ricavato dal LoginManager, potrebbe essere anche tolto perchè si può ricavare dal token inviato visto che l'operazione prevede che l'utente debba essere autenticato
        GetResultsRequest request = new GetResultsRequest(listener);
    }
    //public static void saveResult(PathResult result, AbstractListener listener) { //PathResult non è ancora definito
    //  SaveResultRequest request = new SaveResultRequest(result, listener);
    //}
    public static void getPath(int pathID, AbstractListener listener) { //pathID non sarebbe meglio se fosse una String visto che altrimenti non può cominciare con 0?
        PathRequest request = new PathRequest(pathID, listener);
    }
    public static void getBuildings(double latitude, double longitude, AbstractListener listener) {
        BuildingsRequest request = new BuildingsRequest(latitude, longitude, listener); //nei diagrammi c'è discordanza sulla presenza o meno del numero massimo di edifici
    }
    public static void register(String email, String username, String password) {

    }
    public static void login(String email, String password, AbstractListener listener) {

    }
    public static void logout(String email, AbstractListener listener) { //secondo il nostro diagramma dovrebbe esserci una classe User al posto di email, che però non esiste, da chiedere se per caso è meglio usare un altro dato

    }
}
