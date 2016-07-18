package clips.matteo.authentication;

import android.text.Editable;

/**
 * Created by matteo on 24/06/16.
 */
public class User {
    private final String email;
    private String username;
    private String password;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }


}
