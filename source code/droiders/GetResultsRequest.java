package urlrequest;

import com.android.volley.Request;

import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere l'elenco dei percorsi effettuati dall'utente
public class GetResultsRequest extends URLRequest {
    GetResultsRequest(AbstractListener listener) {
        super(Request.Method.POST, URLDataConstants.baseURL + "", new JSONObject(), true, listener); //NOTA: l'url è da finire
        execute();
    }
}
