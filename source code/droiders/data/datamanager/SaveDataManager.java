package data.datamanager;

import android.content.Context;

import urlrequest.ServerError;

/**
 * Created by andrea on 20/07/16.
 */
public abstract class SaveDataManager {
   protected final AbstractDataManagerListener<Boolean> listener;
   protected final Context cx;

   public SaveDataManager(Context cx, AbstractDataManagerListener<Boolean> listener) {
      this.cx = cx;
      this.listener = listener;
   }

   abstract void execute();

   void returnResponse() {
         listener.onResponse(true);
   } //non viene passato niente perché già la risposta ricevuta indica che l'operazione ha avuto esito positivo

   void returnError(ServerError error) {
      listener.onError(error);
   }
}
