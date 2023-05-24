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
        list.add(new User("0987654321", "111111"));
        list.add(new User("0988888888", "111111"));
        list.add(new User("0977777777", "111111"));
        list.add(new User("0987575701", "111111"));
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
