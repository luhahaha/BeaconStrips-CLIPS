package pathprogress;

import java.util.GregorianCalendar;
import Data.PathProgress;
import Data.Path;
import Data.ProofResult;
import Data.PathResult;

public class PathProgressController{
  private final PathProgress pathProgress;
  private final PathLocalManager pathLocalManager;
  private Bool completed;
  public PathSearchDelegate searchDelegate;

  public PathProgressController(Path path, GregorianCalendar startTime){
    this.pathProgress=new PathProgress(path,startTime);
  }

  public addProofResult(ProofResult result){
    this.pathProgress.addProofResult(result);
  }

  public PathProgress getPathProgress(){
    return this.pathProgress.getPathProgress;
  }

  public complete(GregorianCalendar endTime){
    this.setEndTime(endTime);
  }

  public Bool isCompleted(){
    return completed;
  }

  public PathResult getPathResult(){
    return this.pathProgress.getPathResult();
  }

  public Activity getCurrentActivity(){
      //TODO
  }
  public Bool didFoundBeacon(Beacon beacon){
    //TODO
  }
}
