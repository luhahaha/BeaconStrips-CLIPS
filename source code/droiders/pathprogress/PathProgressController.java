package pathprogress;

import java.util.GregorianCalendar;
import Data.PathProgress;
import Data.Path;
import Data.ProofResult;
import Data.PathResult;

public class PathProgressController{
  public PathProgressControllerDelegate delegate;
  public final Path path;

  public PathProgressController(Path path){
    this.pathProgress=new PathProgress(path,new GregorianCalendar());
  }

  public addProofResult(ProofResult result){
    this.pathProgress.addProofResult(result);
  }

  public PathProgress getPathProgress(){
    return this.pathProgress.getPathProgress;
  }
}
