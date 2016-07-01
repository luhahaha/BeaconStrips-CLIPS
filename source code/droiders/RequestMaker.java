package urlrequest;

/**
 * Created by andrea on 01/07/16.
 */
public class RequestMaker { //questa classe contiene i metodi per effettuare tutti i tipi di richieste al server richiesti all'interno dell'app
    public static void getAppInfo(AbstractListener listener) { //effettua la chiamata per ottenere l'UUID dei beacon e le stringhe con le info dell'app
        AppInfoRequest request = new AppInfoRequest(listener); //ULRequest viene automaticamente inizializzato
    }
}
