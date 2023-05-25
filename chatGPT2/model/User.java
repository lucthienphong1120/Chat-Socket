package chatGPT2.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String name = "";
    private String username = "";
    private String password = "";

    public User() { // constructor for create object
    }
    
    public User(String username, String password) { // constructor for check login
        this.username = username;
        this.password = password;
    }

    public User(String name, String username, String password) {  // constructor for full object
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public ArrayList<User> loadUserData() {
        ArrayList<User> list;
        list = new ArrayList<>();
        list.add(new User("Admin", "admin", "123"));
        list.add(new User("Test", "test", "123"));
        list.add(new User("Chung", "chung", "123"));
        list.add(new User("Dinh", "dinh", "123"));
        list.add(new User("Ky Anh", "kyanh", "123"));
        list.add(new User("Duck", "duc", "123"));
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
}
