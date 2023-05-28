package chatGPT2.model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class UserModel implements Serializable {

    private String name = "Anonymous";
    private String username = "";
    private String password = "";
    private Icon img;
    private boolean online = false;
    private boolean loggin = false;

    public UserModel() { // constructor for create object
    }

    public UserModel(String username, String password) { // constructor for check login user
        this.username = username;
        this.password = password;
    }

    public UserModel(String name, String username, String password, String imgPath) {  // constructor for full object
        this.name = name;
        this.username = username;
        this.password = password;
        this.img = new ImageIcon("./src/chatGPT2/img/" + imgPath);
    }

    public ArrayList<UserModel> loadUserData() {
        ArrayList<UserModel> list;
        list = new ArrayList<>();
        list.add(new UserModel("Admin", "admin", "123", "user.png"));
        list.add(new UserModel("Test", "test", "123", "user.png"));
        list.add(new UserModel("Chung", "chung", "123", "chung.jpg"));
        list.add(new UserModel("Định", "dinh", "123", "dinh.jpg"));
        list.add(new UserModel("Kỳ Lân", "kyanh", "123", "kylan.jpg"));
        list.add(new UserModel("Đúck", "duc", "123", "duc.jpg"));
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

    public Icon getImg() {
        return img;
    }

    public void setImg(String imgPath) {
        this.img  = new ImageIcon("./src/chatGPT2/img/" + imgPath);
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isLoggin() {
        return loggin;
    }

    public void setLoggin(boolean loggin) {
        this.loggin = loggin;
    }

}
