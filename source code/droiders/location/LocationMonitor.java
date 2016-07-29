package location;

import java.util.ArrayList;

/**
 * Created by andrea on 29/07/16.
 */
public abstract class LocationMonitor<DelegateType, RegionType> {
   protected DelegateType delegate;
   protected ArrayList<RegionType> monitoredRegions;

   protected LocationMonitor(DelegateType delegate, ArrayList<RegionType> monitoredRegions) {
      this.delegate = delegate;
      this.monitoredRegions = monitoredRegions;
   }

   protected boolean isAuthorized() {
      return true;
   }

   protected abstract void addRegion(RegionType region);

   protected abstract void removeRegion(RegionType region);
}
