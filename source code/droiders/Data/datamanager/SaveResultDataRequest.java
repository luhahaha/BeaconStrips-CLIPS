package Data.datamanager;

/**
 * Created by andrea on 20/07/16.
 */
public class SaveResultDataRequest extends SaveDataManager { //andrà sicuramente modificata o questa classe o SaveDataManager, altrimenti in execute non finirà mai result
   private Data.PathResult result;

   public SaveResultDataRequest(Data.PathResult result, AbstractDataManagerListener<Integer> listener) {
      super(listener);
      this.result = result;
   }
}
