package beaconstrips.clips.client.pathprogress;

import java.util.GregorianCalendar;
import java.util.Vector;

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

public class PathProgressController implements  BeaconDiscoverDelegate{
  private Vector<RawBeacon> rawbeacons;
  public PathProgressControllerDelegate delegate;
  public PathProgress pathProgress;

  public PathProgressController(Path path, PathProgressControllerDelegate delegate){
    this.rawbeacons=new Vector();
      this.delegate=delegate;
    this.pathProgress=new PathProgress(path, new GregorianCalendar());
  }

  public void addProofResult(ProofResult result){
    this.pathProgress.addProofResult(result);
  }

  public void didFoundBeacon(RawBeacon beacon){
    this.rawbeacons.add(beacon);
    //TODO bisogna controllare che l'utente non stia giocando una prova
    if(beacon.UUID == this.pathProgress.getPath().steps.get(0).stopBeacon.UUID){
      this.delegate.didReachStep(this.pathProgress.getPath().steps.remove(0));
    }
    else{
      Proximity proximity = this.pathProgress.getPath().searchProximity(beacon);
      if(proximity != null)
        this.delegate.didRangeProximity(proximity);
    }
  }

  public void didMoveFromBeacon(RawBeacon beacon){
    this.rawbeacons.remove(beacon);
  }
}
