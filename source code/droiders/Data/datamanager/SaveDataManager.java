package Data.datamanager;

/**
 * Created by andrea on 20/07/16.
 */
public abstract class SaveDataManager {
   protected final AbstractDataManagerListener<Integer> listener;

   public SaveDataManager(AbstractDataManagerListener<Integer> listener) {
      this.listener = listener;
   }

   void execute() {

   }
}
