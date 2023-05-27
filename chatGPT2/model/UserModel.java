package chatGPT2.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {

    private String name = "Anonymous";
    private String username = "";
    private String password = "";

    public UserModel() { // constructor for create object
    }
    
    public UserModel(String username, String password) { // constructor for check login user
        this.username = username;
        this.password = password;
    }

    public UserModel(String name, String username, String password) {  // constructor for full object
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public ArrayList<UserModel> loadUserData() {
        ArrayList<UserModel> list;
        list = new ArrayList<>();
        list.add(new UserModel("Admin", "admin", "123"));
        list.add(new UserModel("Test", "test", "123"));
        list.add(new UserModel("Chung", "chung", "123"));
        list.add(new UserModel("Dinh", "dinh", "123"));
        list.add(new UserModel("Ky Anh", "kyanh", "123"));
        list.add(new UserModel("Duck", "duc", "123"));
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
