public class Leaderboard{
	public final PathInfo pathInfo;
	public final Date lastUpdate;
	public final int nPlayers;
	public final int topScore;
	public final List<Score> scores;
	
	public Leaderboard(PathInfo pathInfo, Date lastUpdate, int nPlayers, int maxScore, QList<Score> scores){
		this.pathInfo = pathInfo;
		this.lastUpdate = lastUpdate;
		this.nPlayers = nPlayers;
		this.maxScore = maxScore;
		this.scores = scores;
	}
}