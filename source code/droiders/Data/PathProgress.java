public class PathProgress{
	private final Path path;
	private final Date startTime;
	private Date endTime;
	private List<ProofResult> proofResults;
	
	public PathProgress(Path path, Date startTime){
		this.path = path;
		this.startTime = startTime;
	}
	
	public Path getPath(){
		return path;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	
	//TODO correggere calcolo durata
	public long getDuration(){
		return endTime.getTime() - startTime.getTime();
	}
	
	public void addProofResult(ProofResult proofResult){
		proofResults.add(proofResult);
	}
	
	public List<ProofResult> getProofResults(){
		return proofResults;
	}
	
	public int getTotalScore(){
		int score = 0;
		for(int i=0; i < proofResults.size(); ++i){
			totalScore += proofResults.get(i).score;
		}
		return score;
	}
		
}