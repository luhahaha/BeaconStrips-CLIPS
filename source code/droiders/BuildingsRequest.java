package urlrequest;

import com.android.volley.Request;

import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere l'elenco degli edifici abilitati più vicini alla posizione inviata, per ogni edificio viene inviata anche la lista dei percorsi presenti
public class BuildingsRequest extends URLRequest {
    BuildingsRequest(double latitude, double longitude, AbstractListener listener) { //c'è discordanza nei diagrammi per quanto riguarda la presenza del numero massimo di edifici
        super(Request.Method.POST, URLDataConstants.baseURL + "", getBody(latitude, longitude), false, listener); //l'url è da finire, ho messo POST perchè col tempo l'invio di queste informazioni potrebbe diventare pesante
        execute();
    }
    static JSONObject getBody(double latitude, double longitude) {
        JSONObject body = new JSONObject();
        body.put("latitude", latitude);
        body.put("longitude", longitude);
        return body;
    }
}
