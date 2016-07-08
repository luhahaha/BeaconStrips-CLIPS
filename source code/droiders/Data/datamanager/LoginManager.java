package Data.datamanager;

/**
 * Created by andrea on 08/07/16.
 */

//QUESTA È UNA CLASSE DI PROVA PER I TEST, INFATTI NON USA ULTERIORI CLASSI E IL TOKEN VIENE SALVATO A MANO
public class LoginManager {
    private String token; //aggiungere il token stabilito
    private static LoginManager singleInstance;
    private LoginManager() {token="";}
    public String getToken() {return token;}
    public static LoginManager sharedManager() {
        if(singleInstance==null)
            singleInstance=new LoginManager();
        return singleInstance;
    }
}