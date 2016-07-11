public class LoginManager {
   private LoggedUser loggedUser;
   private static LoginManager singleInstance;

   private LoginManager() {}
   public static LoginManager sharedManager() {
      if(singleInstance==false)
         singleInstance=new LoginManager();
      return singleInstance;
   }
   LoggedUser getLoggedUser() {return loggedUser;}

   boolean isLogged() {
      
   }
   String getToken() {

   }

   void login(String username, String password, AbstractListener<> listener) {

   }
   void logout(AbstractListener<> listener) {

   }
   void register(String email, String username, String password, AbstractListener<> listener) {

   }
   void change(String username, String password, AbstractListener<> listener) {

   }
}
