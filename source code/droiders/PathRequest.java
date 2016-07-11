package urlrequest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere i dati del percorso selezionato, comprese tutte le prove da giocare
public class PathRequest extends URLRequest { //pathID per me dovrebbe essere String e non int
    PathRequest(Context cx, int pathID){
        super(cx, Request.Method.POST, URLDataConstants.baseURL + "path/" + pathID, null, false, new PathRequestListener()); //l'url è da finire
        execute();
    }
}



class PathRequestListener extends URLRequestListener{

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onError(VolleyError error) {

    }
}
