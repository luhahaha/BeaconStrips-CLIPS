public class LoggedUser{
	public final int id;
	public final String username;
	public final String email;
	public final String token;
	
	public LoggedUser(int id, String username, String email, String token){
		this.id = id;
		this.username = username;
		this.email = email;
		this.token = token;
	}
}