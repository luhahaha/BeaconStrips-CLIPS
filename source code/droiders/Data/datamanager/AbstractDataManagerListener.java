package Data.datamanager;

import com.android.volley.VolleyError;

/**
 * Created by andrea on 19/07/16.
 */

//la classe astratta da cui derivano i listener del DataManager, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
public abstract class AbstractDataManagerListener<ModelObject> {
   public abstract void onResponse(ModelObject response);

   public abstract void onError(VolleyError error); //Probabilmente anche questo tipo sarà da cambiare (ad esempio con una string o un numero) perché l'errore di volley forse dev'essere già interpretato dal datamanager
}
