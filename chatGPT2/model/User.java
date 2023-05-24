package chatGPT2.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String username = "";
    private String password = "";

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ArrayList<User> loadUserData() {
        ArrayList<User> list;
        list = new ArrayList<>();
        list.add(new User("admin", "123"));
        list.add(new User("test", "123"));
        list.add(new User("chung", "123"));
        list.add(new User("dinh", "123"));
        list.add(new User("kyanh", "123"));
        list.add(new User("duc", "123"));
        return list;
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
