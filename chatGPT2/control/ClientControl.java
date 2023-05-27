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
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClientControl {

    // import global variables
    private String serverName = "localhost";
    private int serverPort = 1234;
    private String rmiField = "server";
    private int rmiPort = 1099;
    private Socket connection;
    OutputStream outToServer;
    ObjectOutputStream objOutput;
    InputStream inFromServer;
    ObjectInputStream objInput;
    // import objects
    private ChatView chatView;
    private LoginView loginView;
    private UserModel user = new UserModel();
    private MessageModel messageModel = new MessageModel();
    private ArrayList<UserModel> list;
    private ServerInterface serverRMI;
    List<MessageModel> messageList;

    public ClientControl(String serverName, int serverPort, String rmiField, int rmiPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.rmiField = rmiField;
        this.rmiPort = rmiPort;
        list = user.loadUserData();
    }

    private void connectRMI() {
        try {
            serverRMI = (ServerInterface) Naming.lookup("rmi://" + serverName + ":" + rmiPort + "/" + rmiField);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connecting() {
        try {
            // open connections
            connection = new Socket(serverName, serverPort);
            connectRMI();
            // send message
            outToServer = connection.getOutputStream();
            objOutput = new ObjectOutputStream(outToServer);
            // get message
            inFromServer = connection.getInputStream();
            objInput = new ObjectInputStream(inFromServer);
            if (!user.isLoggin() && !user.isOnline()) {
                openLogin();
            }
            while (connection.isConnected()) {
                if (user.isLoggin() && !user.isOnline()) {
                    openChat();
                    // send data to server
                    objOutput.writeObject(user);
                    objOutput.flush();
                }
                if (user.isLoggin() && user.isOnline()) {
                    // get data from server
                    Object obj = objInput.readObject();
                    if (obj instanceof List<?>) {
                        List<?> receivedList = (List<?>) obj;
                        if (!receivedList.isEmpty() && receivedList.get(0) instanceof MessageModel) {
                            messageList = (List<MessageModel>) obj;
                            System.out.println(messageList.size());
                        } else {
                            System.out.println("Received object is not a List of MessageModel");
                        }
                    } else {
                        System.out.println("Received object is not a List");
                    }
                }

                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inFromServer.close();
                outToServer.close();
                objInput.close();
                objOutput.close();
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
                user = new UserModel(username, password);
                if (checkLogin(user)) {
                    // Xử lý logic khi đăng nhập thành công
                    JOptionPane.showMessageDialog(loginView, "Login successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Đổi trạng thái
                    user.setLoggin(true);;
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
        chatView.Title.setText("Welcome back " + user.getName());
        user.setOnline(true);
        ActionListener e = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageModel = new MessageModel(user.getName(),
                        chatView.jTextMessage.getText(),
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                sendMessage(chatView.jTextArea, messageModel);
                chatView.jTextMessage.setText("");
            }
        };
        chatView.jTextMessage.addActionListener(e);
        chatView.jSend.addActionListener(e);
    }

    public boolean checkLogin(UserModel user) {
        for (UserModel availableUser : list) {
            if (user.getUsername().equals(availableUser.getUsername())
                    && user.getPassword().equals(availableUser.getPassword())) {
                user.setName(availableUser.getName());
                return true;
            }
        }
        return false;
    }

    public void sendMessage(JTextArea jTextArea, MessageModel messageModel) {
        try {
            // Gửi thông tin tin nhắn cho server
            objOutput.writeObject(messageModel);
            objOutput.flush();
            // Cập nhật tin nhắn mới trong JTextArea
            String senderName = messageModel.getName();
            String sentTime = messageModel.getTime();
            String message = messageModel.getMessage();
            String newMessage = "[" + senderName + "] (" + sentTime + "): " + message + "\n";
            jTextArea.append(newMessage);
            // Cuộn xuống cuối tin nhắn mới
            jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
