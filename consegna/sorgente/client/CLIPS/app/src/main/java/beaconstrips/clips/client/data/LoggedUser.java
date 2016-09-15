package beaconstrips.clips.client.data;

/**
 * @file LoggedUser.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta i dati del profilo dell'utente
 */
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