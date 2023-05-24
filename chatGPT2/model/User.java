package chatGPT2.model;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof User) {
            User user = (User) obj;
            return this.username.equals(user.getUsername())
                    && this.password.equals(user.getPassword());
        }
        return false;
    }
    
}
