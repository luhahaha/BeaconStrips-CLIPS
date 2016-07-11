public class PathResult{
	public final int userID;
	public final Path path;
	public final Date startTime;
	public final Date startTime;
	public final List<ProofResult> proofResults;
	
	public PathResult(int user, Path path, Date startTime, Date endTime, List<ProofResult> proofResults){
		this.userID = user;
		this.path = path;
		this.startTime = startTime;
		this.endTime = endTime;
		this.proofResults = proofResults;
	}
}