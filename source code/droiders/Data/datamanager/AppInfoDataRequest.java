package Data.datamanager;

import android.content.Context;

/**
 * Created by andrea on 26/07/16.
 */
public class AppInfoDataRequest extends DataManager {
   AppInfoDataRequest(Context cx, AbstractDataManagerListener<String []> listener) {
      super(cx, CachePolicy.AlwaysReplaceLocal, listener);
      execute();
   }

   protected String queryForLocalData() {
      return "";
   }

   protected String[] parseFromLocal() {
      return new String[]{"", ""};
   }

   protected void getRemoteData() {

   }

   protected void urlRequest() {

   }

   protected String[] parseFromUrlRequest(){
      return new String[]{"", ""};
   }

   protected void updateLocalData(){

   }

   protected String getUpdateLocalDataQuery() {
      return "";
   }
}
