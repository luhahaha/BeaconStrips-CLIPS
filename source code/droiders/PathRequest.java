package urlrequest;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere i dati del percorso selezionato, comprese tutte le prove da giocare
public class PathRequest extends URLRequest { //pathID per me dovrebbe essere String e non int
    PathRequest(int pathID){
        super(Request.Method.POST, URLDataConstants.baseURL + "", setBody(pathID), false, new PathRequestListener()); //l'url è da finire
        execute();
    }


    private static JSONObject setBody(int pathID) {
        JSONObject body = new JSONObject();
        try{
            body.put("pathID", pathID);
        }
        catch(JSONException e){

        }
        return body;
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