package urlrequest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere l'elenco dei percorsi effettuati dall'utente
public class GetResultsRequest extends URLRequest {
    GetResultsRequest(Context cx) {
        super(cx, Request.Method.GET, URLDataConstants.baseURL + "", null, true, new GetResultsListener()); //NOTA: l'url è da finire
        execute();
    }
}




class GetResultsListener extends URLRequestListener{

    @Override
    public void onResponse(JSONObject response) {
        //passa l'elenco dei percorsi all'oggetto che li ha richiesti
    }

    @Override
    public void onError(VolleyError error) {

    }
}
