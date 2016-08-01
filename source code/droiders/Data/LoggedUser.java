package Data;

public class LoggedUser {
   public final String username;
   public final String email;
   public final String token;

   public LoggedUser(String token, String email, String username) {
      this.username = username;
      this.email = email;
      this.token = token;
   }
}