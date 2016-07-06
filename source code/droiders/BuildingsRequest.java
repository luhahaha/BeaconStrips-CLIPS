package urlrequest;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere l'elenco degli edifici abilitati più vicini alla posizione inviata, per ogni edificio viene inviata anche la lista dei percorsi presenti
public class BuildingsRequest extends URLRequest {

    //c'è discordanza nei diagrammi per quanto riguarda la presenza del numero massimo di edifici
    BuildingsRequest(Context cx, double latitude, double longitude) {
        super(cx, Request.Method.POST, URLDataConstants.baseURL + "", setBody(latitude, longitude), false, new BuildingsRequestListener()); //l'url è da finire, ho messo POST perchè col tempo l'invio di queste informazioni potrebbe diventare pesante
        execute();
    }

    private static JSONObject setBody(double latitude, double longitude) {
        JSONObject body = new JSONObject();
        try
        {
            body.put("latitude", latitude);
            body.put("longitude", longitude);
        }
        catch(JSONException e)
        {

        }

        return body;
    }
}



class BuildingsRequestListener extends URLRequestListener{

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onError(VolleyError error) {

    }
}
