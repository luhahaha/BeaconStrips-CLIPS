package Data;

import java.util.Date;
import java.util.List;

public class PathResult2{
	public final int userID;
	public final Path path;
	public final Date startTime;
	public final Date endTime;
	public final List<ProofResult> proofResults;
	
	public PathResult2(int user, Path path, Date startTime, Date endTime, List<ProofResult> proofResults){
		this.userID = user;
		this.path = path;
		this.startTime = startTime;
		this.endTime = endTime;
		this.proofResults = proofResults;
	}
}