package urlrequest;

import com.android.volley.Request;

import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere i dati del percorso selezionato, comprese tutte le prove da giocare
public class PathRequest extends URLRequest { //pathID per me dovrebbe essere String e non int
    PathRequest(int pathID, AbstractListener listener){
        super(Request.Method.POST, URLDataConstants.baseURL + "", getBody(pathID), false, listener); //l'url è da finire
        execute();
    }
    static JSONObject getBody(int pathID) {
        JSONObject body = new JSONObject();
        body.put("pathID", pathID); //per qualche oscuro motivo mi da errore, il metodo JSONObject.put(String, int) esiste nella documentazione ufficiale, cercando su Internet parrebbe che sia obbligatorio mettere l'istruzione in un blocco try/catch e lanciare una specifica eccezione
        return body;
    }
}
