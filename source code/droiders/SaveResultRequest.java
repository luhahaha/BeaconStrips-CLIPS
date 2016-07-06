package urlrequest;

import com.android.volley.Request;

import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

/**classe che inizializza l'URLRequest per inviare i risultati salvati del percorso appena giocato*/
/* PathResult non è ancora stato definito
public class SaveResultRequest extends URLRequest {
    SaveResultRequest(Context cx, PathResult result, URLRequestListener listener){
        super(cx, Request.Method.POST, URLDataConstants.baseURL + "", getBody(result), true, listener); //l'url è da finire
        execute();
    }
    //dato che la chiamata super() deve precedere qualunque altra istruzione costruisco il body in questo metodo, è statico perché altrimenti non può essere usato nel super()
    static JSONObject getBody(PathResult result) {
        JSONObject body = new JSONObject();
        body.put("Result", result);
        return body;
    }
}
*/
