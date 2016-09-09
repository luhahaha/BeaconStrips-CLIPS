package beaconstrips.clips.client.pathprogress;

import android.util.Log;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.io.Serializable;
import java.util.GregorianCalendar;

import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.PathProgress;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.Proximity;

/**
 * @file PathProgressController.java
 * @date 22/07/16
 * @version 1.0.0
 * @author Luca Soldera
 *
 * classe che gestisce il progresso dei percorsi
 */

public class PathProgressController implements BeaconDiscoverDelegate, Serializable {
  private int index;
  public PathProgressControllerDelegate delegate;
  public PathProgress pathProgress;
  private String TAG ="PathProgressController";

    public void setDelegate(PathProgressControllerDelegate delegate){
        this.delegate=delegate;
    }
  public PathProgressController(Path path){
      this.index=0;
    this.pathProgress=new PathProgress(path, new GregorianCalendar());
  }

  public void savedResult(ProofResult result){
    this.pathProgress.addProofResult(result);
      if(index!=this.pathProgress.getPath().steps.size()) {
          this.delegate.pathEnded(pathProgress.getTotalScore());
      }
  }


  public void didFoundBeacon(IBeaconDevice beacon){
    Log.i(TAG, "Called didFoundBeacon");
          if (beacon.getProximityUUID().equals(java.util.UUID.fromString(this.pathProgress.getPath().steps.get(index).stopBeacon.UUID)) && beacon.getMajor() == this.pathProgress.getPath().steps.get(index).stopBeacon.major
                  && beacon.getMinor() == this.pathProgress.getPath().steps.get(index).stopBeacon.minor) {
              this.delegate.didReachProof(this.pathProgress.getPath().steps.get(index).proof);
              index++;
          } else {
              Proximity proximity = this.pathProgress.getPath().searchProximity(beacon,index);
              if(proximity != null)
              this.delegate.didRangeProximity(proximity.percentage,proximity.textToDisplay);
          }
  }

    public void didMoveFromBeacon(RawBeacon beacon){
  }
}
