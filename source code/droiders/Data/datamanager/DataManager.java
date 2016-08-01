package Data.datamanager;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by andrea on 20/07/16.
 */
public abstract class DataManager<Data> {
   public enum CachePolicy {
      NoCache, AlwaysReplaceLocal, LocalElseRemote
   }

   protected final AbstractDataManagerListener<Data> listener; //se poi viene dichiarato private il final non serve
   public final CachePolicy cachePolicy; //se poi viene dichiarato private il final non serve
   protected Data cachedData; //sono i dati salvati in locale, ovviamente il campo è inizializzato solo se ci sono dati salvati in locale e se la polizza di cache prevede il salvataggio in locale
   protected Data remoteData; //sono i dati ricevuti dal server, ovviamente il campo è inizializzato solo se la polizza permette di richiedere i dati al server
   protected final Context cx;

   DataManager(Context cx, CachePolicy cachePolicy, AbstractDataManagerListener<Data> listener) {
      this.cx=cx;
      this.cachePolicy=cachePolicy;
      this.listener=listener;
   }

   void execute() {
      if(cachePolicy==CachePolicy.NoCache) {
         getRemoteData(new urlrequest.AbstractUrlRequestListener() { //con il LoginManager l'inizializzazione di variabili mi dava errore, qui no ma comunque è bene controllare per bene che vada tutto a buon fine
            public void onResponse(JSONObject response) {
               remoteData = parseFromUrlRequest(response);
               listener.onResponse(remoteData);
            }
            public void onError(urlrequest.ServerError error) {listener.onError(error);}
         });
      }
      else if(cachePolicy==CachePolicy.AlwaysReplaceLocal) {
         getRemoteData(new urlrequest.AbstractUrlRequestListener() {
            public void onResponse(JSONObject response) {
               remoteData = parseFromUrlRequest(response);
               if(remoteData==null) {
                  cachedData=parseFromLocal();
                  listener.onResponse(cachedData);
               }
               else {
                  updateLocalData(remoteData);
                  listener.onResponse(remoteData);
               }
            }
            public void onError(urlrequest.ServerError error) {listener.onError(error);}
         });
      }
      else { //cachePolicy==CachePolicy.LocalElseRemote
         cachedData=parseFromLocal();
         if(cachedData==null) {
            getRemoteData(new urlrequest.AbstractUrlRequestListener() {
               public void onResponse(JSONObject response) {
                  remoteData = parseFromUrlRequest(response);
                  updateLocalData(remoteData);
                  listener.onResponse(remoteData);
               }
               public void onError(urlrequest.ServerError error) {listener.onError(error);}
            });
         }
         else {
            listener.onResponse(cachedData);
         }
      }
   }

   protected abstract Data parseFromLocal();
   protected abstract void getRemoteData(urlrequest.AbstractUrlRequestListener listener);
   protected abstract Data parseFromUrlRequest(JSONObject response);
   protected abstract void updateLocalData(Data dataToReplace); //Deve fare il controllo
}
