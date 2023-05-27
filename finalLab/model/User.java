package finalLab.model;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {

    private String username;
    private String password;
    private String status;
    private int port;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.port = new Random().nextInt(9000) + 1000; // random port 1000-9999
    }

    public User(String username, String password, String status) {
        this.username = username;
        this.password = password;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
