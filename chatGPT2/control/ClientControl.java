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
    private String serverName = "localhost";
    private int serverPort = 1234;
    private int rmiPort = 1099;
    private Socket connection;
    private User user = new User();
    private UserState state = new UserState();
    // import objects
    private ChatView chatView;
    private LoginView loginView;
    private ArrayList<User> list;

    public ClientControl(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        list = user.loadUserData();
    }

    public void connecting() {
        try {
            connection = new Socket(serverName, serverPort);
            if (state.getCurrentState() == UserState.NOT_LOGIN) {
                openLogin();
            }
            while (true) {
                if (state.getCurrentState() == UserState.CONNECTED) {
                    // send message
                    OutputStream outToServer = connection.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    out.writeUTF("[Client] Hello from " + connection.getLocalSocketAddress());
                    // get message
                    InputStream inFromServer = connection.getInputStream();
                    DataInputStream in = new DataInputStream(inFromServer);
                    System.out.println(in.readUTF());
                }
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                    state.setCurrentState(UserState.CONNECTED);
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

    public boolean checkLogin(User user) {
        for (User u : list) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }
}
