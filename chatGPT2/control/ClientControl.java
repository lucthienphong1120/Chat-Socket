package chatGPT2.control;

import chatGPT2.view.*;
import chatGPT2.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClientControl {

    // import global variables
    private String serverName;
    private int serverPort;
    private Socket connection;
    private User user;
    private int state;
    // import objects
    private ChatView chatView;
    private LoginView loginView;
    private ArrayList<User> list;

    public ClientControl(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.state = UserState.NOT_LOGIN;
        this.loadUserData();
    }

    public void connecting() {
        try {
            connection = new Socket(serverName, serverPort);
            openLogin();
            // send message
            while (this.state == UserState.NOT_LOGIN) {
                OutputStream outToServer = connection.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                out.writeUTF("[Client] Hello from " + connection.getLocalSocketAddress());
                // get message
                InputStream inFromServer = connection.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                System.out.println(in.readUTF());
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openLogin() {
        loginView = new LoginView();
        loginView.setVisible(true);
        loginView.jLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginView.jUsername.getText();
                String password = loginView.jPassword.getText();
                user = new User(username, password);
                if (checkLogin(user)) {
                    // Huỷ lắng nghe sự kiện
                    loginView.jLogin.removeActionListener(this);
                    JOptionPane.showMessageDialog(loginView, "Login successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Xử lý logic khi đăng nhập không thành công
                    JOptionPane.showMessageDialog(loginView, "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void loadUserData() {
        list = new ArrayList<>();
        list.add(new User("0987654321", "111111"));
        list.add(new User("0988888888", "111111"));
        list.add(new User("0977777777", "111111"));
        list.add(new User("0987575701", "111111"));
    }

    public boolean checkLogin(User user) {
        for (User u : list) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }
}
