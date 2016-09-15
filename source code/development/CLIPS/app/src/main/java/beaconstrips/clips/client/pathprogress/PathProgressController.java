package beaconstrips.clips.client.pathprogress;

import android.util.Log;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

  public boolean savedResult(GregorianCalendar startTime, GregorianCalendar finishTime, int correct, int total){
     index++;
     Log.i(TAG, "Duration is " + getDuration(startTime, finishTime));
     Log.i(TAG, "Score of proof is " + this.pathProgress.getPath().steps.get(index-1).proof.scoringAlgorithm.getScore(this.getDuration(startTime,finishTime), correct, total));
      ProofResult result= new ProofResult(this.pathProgress.getPath().steps.get(index-1).proof.id,startTime,finishTime,this.pathProgress.getPath().steps.get(index-1).proof.scoringAlgorithm.getScore(this.getDuration(startTime,finishTime),correct,total));

    this.pathProgress.addProofResult(result);
      if(index == this.pathProgress.getPath().steps.size()) {
          return true;
      }
      return false;
  }

   public double getTotalScore() {
      double total = 0.0;
      List<ProofResult> proofResults = pathProgress.getProofResults();
      for(int i = 0; i < proofResults.size(); ++i) {
         total += proofResults.get(i).score;
      }
      Log.i(TAG, "Total score is " + total);
      return total;
   }


  public void didFoundBeacon(IBeaconDevice beacon){
     Log.e(TAG, "Index is " + index);
          if (beacon.getProximityUUID().equals(java.util.UUID.fromString(this.pathProgress.getPath().steps.get(index).stopBeacon.UUID)) &&
                  beacon.getMajor() == this.pathProgress.getPath().steps.get(index).stopBeacon.major &&
                  beacon.getMinor() == this.pathProgress.getPath().steps.get(index).stopBeacon.minor
                  && (beacon.getProximity() == com.kontakt.sdk.android.common.Proximity.NEAR || beacon.getProximity() == com.kontakt.sdk.android.common.Proximity.IMMEDIATE)) {
             Log.i(TAG, "Beacon to find " + this.pathProgress.getPath().steps.get(index).stopBeacon.UUID);
             Log.i(TAG, "Beacon to find " + this.pathProgress.getPath().steps.get(index).stopBeacon.major);
             Log.i(TAG, "Beacon to find " + this.pathProgress.getPath().steps.get(index).stopBeacon.minor);

             Log.i(TAG, "Beacon found" + beacon.getMajor());
             Log.i(TAG, "Beacon found" + beacon.getMinor());
             Log.i(TAG, "Beacon found" + beacon.getProximityUUID());
             Log.i(TAG, "Proximity " + beacon.getProximity());
              this.delegate.didReachProof(this.pathProgress.getPath().steps.get(index).proof);
          } else {
              Proximity proximity = this.pathProgress.getPath().searchProximity(beacon,index);
              if(proximity != null)
               this.delegate.didRangeProximity(proximity.percentage,proximity.textToDisplay);
          }
  }

    public double getDuration(GregorianCalendar startTime, GregorianCalendar endTime) {
        return TimeUnit.MILLISECONDS.toSeconds(endTime.getTime().getTime() - startTime.getTime().getTime());
    }
    public void didMoveFromBeacon(RawBeacon beacon){
  }
}
