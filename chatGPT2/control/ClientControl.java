package chatGPT2.control;

import chatGPT2.view.*;
import chatGPT2.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClientControl {

    // import global variables
    private String serverName = "localhost";
    private int serverPort = 1234;
    private int rmiPort = 1099;
    private Socket connection;
    OutputStream outToServer;
    ObjectOutputStream out;
    InputStream inFromServer;
    ObjectInputStream in;
    // import objects
    private ChatView chatView;
    private LoginView loginView;
    private User user = new User();
    private UserState state = new UserState();
    private MessageModel messageModel = new MessageModel();
    private ArrayList<User> list;

    public ClientControl(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        list = user.loadUserData();
    }

    public void connecting() {
        try {
            connection = new Socket(serverName, serverPort);
            // send message
            outToServer = connection.getOutputStream();
            out = new ObjectOutputStream(outToServer);
            // get message
            inFromServer = connection.getInputStream();
            in = new ObjectInputStream(inFromServer);
            if (state.getCurrentState() == UserState.NOT_LOGIN) {
                openLogin();
            }
            while (true) {
                if (state.getCurrentState() == UserState.LOGGED) {
                    openChat();
                    out.writeObject(user);
                    state.setCurrentState(UserState.CONNECTED);
                }
                if (state.getCurrentState() == UserState.CONNECTED) {
                    // xử lý gì đó
                }
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inFromServer.close();
                outToServer.close();
                in.close();
                out.close();
                connection.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void openLogin() {
        loginView = new LoginView();
        loginView.setVisible(true);
        ActionListener e = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginView.jUsername.getText();
                String password = loginView.jPassword.getText();
                user = new User(username, password);
                if (checkLogin(user)) {
                    // Xử lý logic khi đăng nhập thành công
                    JOptionPane.showMessageDialog(loginView, "Login successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Đổi trạng thái
                    state.setCurrentState(UserState.LOGGED);
                    // Đóng view
                    loginView.dispose();
                    // Huỷ lắng nghe sự kiện
                    loginView.jLogin.removeActionListener(this);
                } else {
                    // Xử lý logic khi đăng nhập không thành công
                    JOptionPane.showMessageDialog(loginView, "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        loginView.jPassword.addActionListener(e);
        loginView.jLogin.addActionListener(e);
    }

    private void openChat() {
        chatView = new ChatView();
        chatView.setVisible(true);
        ActionListener e = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageModel = new MessageModel(user.getUsername(), chatView.jTextMessage.getText(), new Date());
                sendMessage(chatView.jTextArea, messageModel);
                chatView.jTextMessage.setText("");
            }
        };
        chatView.jTextMessage.addActionListener(e);
        chatView.jSend.addActionListener(e);
    }

    public boolean checkLogin(User user) {
        for (User u : list) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void sendMessage(JTextArea jTextArea, MessageModel messageModel) {
        // Cập nhật tin nhắn mới trong JTextArea
        String senderName = messageModel.getName();
        String sentTime = new SimpleDateFormat("HH:mm:ss").format(messageModel.getTime());
        String message = messageModel.getMessage();
        String newMessage = "[" + senderName + "] (" + sentTime + "): " + message + "\n";
        jTextArea.append(newMessage);
        // Cuộn xuống cuối tin nhắn mới
        jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
    }
}
