package Data.datamanager;

import android.content.Context;

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
         remoteData = parseFromUrlRequest();
         listener.onResponse(remoteData);
      }
      else if(cachePolicy==CachePolicy.AlwaysReplaceLocal) {
         remoteData = parseFromUrlRequest();
         if(remoteData==null) {
            cachedData=parseFromLocal();
            listener.onResponse(cachedData);
         }
         else {
            listener.onResponse(remoteData);
         }
      }
      else { //cachePolicy==CachePolicy.LocalElseRemote
         cachedData=parseFromLocal();
         if(cachedData==null) {
            remoteData=parseFromUrlRequest();
            listener.onResponse(remoteData);
         }
         else {
            listener.onResponse(cachedData);
         }
      }
   }

   protected void getLocalData() { //non serve caricare listener perché è già accessibile da questa classe

   }

   protected abstract String queryForLocalData();
   protected abstract Data parseFromLocal();
   protected abstract void getRemoteData(); //non serve caricare listener perché è già accessibile da questa classe, il tipo di ritorno non so quale sia ma sicuramente non sarà void
   protected abstract void urlRequest(); //secondo me il tipo di ritorno URLRequest del diagramma è sbagliato, ha più senso piuttosto chiamare il metodo corretto di RequestMaker
   protected abstract Data parseFromUrlRequest();
   protected abstract void updateLocalData();
   protected abstract String getUpdateLocalDataQuery();
}
