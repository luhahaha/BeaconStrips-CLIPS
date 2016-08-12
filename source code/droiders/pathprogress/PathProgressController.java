package pathprogress;

import java.util.GregorianCalendar;
import java.util.Vector;
import Data.PathProgress;
import Data.Path;
import Data.ProofResult;
import Data.PathResult;
import java.util.Context;

public class PathProgressController implements  BeaconDiscoverDelegate{
  private Vector<RawBeacon> rawbeacons;
  public PathProgressControllerDelegate delegate;
  public PathProgress pathProgress;

  public PathProgressController(Path path){
    this.rawbeacons=new Vector();
    this.delegate=this;
    this.pathProgress=new PathProgress(path, new GregorianCalendar());
  }

  public addProofResult(ProofResult result){
    this.pathProgress.addProofResult(result);
  }

  public PathProgress getPathProgress(){
    return this.pathProgress.getPathProgress();
  }

  public void didFoundBeacon(RawBeacon beacon){
    this.rawbeacons.add(beacon);
    //TODO bisogna controllare che l'utente non stia giocando una prova
    if(beacon.UUID == this.pathProgress.getPath().steps.get(0).stopBeacon.UUID){
      this.delegate.didReachStep(this.pathProgress.getPath().steps.remove(0));
    }
    else{
      Proximity proximity =this.pathProgress.getPath().steps.searchProximity(beacon);
      if(proximity != null)
      this.delegate.didRangeProximity(proximity);
    }
  }

  public void didMoveFromBeacon(RawBeacon beacon){
    this.rawbeacons.remove(beacon);
  }
}
