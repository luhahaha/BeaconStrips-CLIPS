package beaconstrips.clips.client.data.datamanager;

import android.content.Context;

import org.json.JSONObject;

import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file DataManager.java
 * @date 20/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe astratta per ottenere i dati richiesti dall'applicazione tramite le operazioni dichiarate e gestite dalla classe stessa, che verranno implementate dalle classi derivate
 */
public abstract class DataManager<Data> {
   public enum CachePolicy {
      NoCache, AlwaysReplaceLocal, LocalElseRemote
   }

   protected final AbstractDataManagerListener<Data> listener;
   protected final CachePolicy cachePolicy;
   protected Data cachedData; //sono i dati salvati in locale, ovviamente il campo è inizializzato solo se ci sono dati salvati in locale e se la polizza di cache prevede il salvataggio in locale
   protected Data remoteData; //sono i dati ricevuti dal server, ovviamente il campo è inizializzato solo se la polizza permette di richiedere i dati al server
   protected final Context cx;

   DataManager(Context cx, CachePolicy cachePolicy, AbstractDataManagerListener<Data> listener) {
      this.cx=cx;
      this.cachePolicy=cachePolicy;
      this.listener=listener;
   }

   protected void execute() {
      if(cachePolicy== CachePolicy.NoCache) {
         getRemoteData(new AbstractUrlRequestListener() {
            public void onResponse(JSONObject response) {
               remoteData = parseFromUrlRequest(response);
               listener.onResponse(remoteData);
            }
            public void onError(ServerError error) {listener.onError(error);}
         });
      }
      else if(cachePolicy== CachePolicy.AlwaysReplaceLocal) {
         getRemoteData(new AbstractUrlRequestListener() {
            public void onResponse(JSONObject response) {
               remoteData = parseFromUrlRequest(response);
               updateLocalData(remoteData);
               listener.onResponse(remoteData);
            }
            public void onError(ServerError error) {//remoteData non viene inizializzato in due casi: o il server non è raggiungibile, e quindi bisogna prelevare i dati in locale, oppure il JSON non viene parsato correttamente, e quindi deve restituire un errore
               cachedData = parseFromLocal();
               if(cachedData!=null) {
                  listener.onResponse(cachedData);
               } else {
                  listener.onError(error);
               }
            }
         });
      }
      else { //cachePolicy==CachePolicy.LocalElseRemote
         cachedData=parseFromLocal();
         if(cachedData==null) {
            getRemoteData(new AbstractUrlRequestListener() {
               public void onResponse(JSONObject response) {
                  remoteData = parseFromUrlRequest(response);
                  updateLocalData(remoteData);
                  listener.onResponse(remoteData);
               }
               public void onError(ServerError error) {listener.onError(error);}
            });
         }
         else {
            listener.onResponse(cachedData);
         }
      }
   }

   protected abstract Data parseFromLocal();
   protected abstract void getRemoteData(AbstractUrlRequestListener listener);
   protected abstract Data parseFromUrlRequest(JSONObject response);
   protected abstract void updateLocalData(Data dataToReplace);
}
