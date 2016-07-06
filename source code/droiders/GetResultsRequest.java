package urlrequest;

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
        super(cx, Request.Method.POST, URLDataConstants.baseURL + "", setBody(), true, new GetResultsListener()); //NOTA: l'url è da finire
        execute();
    }

    private static JSONObject setBody(){
        JSONObject body = new JSONObject();

        //TODO metodo che ritorna l'email dell'utente loggato
      /*try{
         body.put("email", getLoggedEmail());
      }
      catch(JSONException e){

      }*/
        return body;
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
