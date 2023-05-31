package Messenger.control;

import Messenger.model.*;
import Messenger.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
import javax.swing.text.BadLocationException;

public class ClientControl {

    // import global variables
    private String serverName = "localhost";
    private int serverPort = 1234;
    private String rmiField = "serverRMI";
    private int rmiPort = 1099;
    private Socket connection;
    private OutputStream outToServer;
    private ObjectOutputStream objOutput;
    private InputStream inFromServer;
    private ObjectInputStream objInput;
    // import objects
    private ChatView chatView;
    private LoginView loginView;
    private UserModel user = new UserModel();
    private MessageModel messageModel = new MessageModel();
    private ArrayList<UserModel> onlineUsers = new ArrayList<>();
    private List<MessageModel> messageList;
    private RMIServerInterface serverRMI;

    public ClientControl(String serverName, int serverPort, String rmiField, int rmiPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.rmiField = rmiField;
        this.rmiPort = rmiPort;
    }

    public void setupRMI(String username, int port) {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(username, new RMIClientImpl());
            System.out.println("Dang ky thanh cong Client RMI");
        } catch (RemoteException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connecting() {
        try {
            // open connections
            connection = new Socket(serverName, serverPort);
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
                if (!user.isLoggin() && !user.isOnline()) {
                    // nhận kết quả đăng nhập từ server
                    Object obj = objInput.readObject();
                    if (obj instanceof UserModel) {
                        user = (UserModel) obj;
                        // tạo RMI cho client
                        setupRMI(user.getUsername(), user.getPort());
                        // Xử lý logic khi đăng nhập thành công
                        JOptionPane.showMessageDialog(loginView, "Login successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Đổi trạng thái
                        user.setLoggin(true);;
                        // Đóng view
                        loginView.dispose();
                    } else {
                        // Xử lý logic khi đăng nhập không thành công
                        JOptionPane.showMessageDialog(loginView, "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (user.isLoggin() && !user.isOnline()) {
                    openChat();
                    // Đổi trạng thái
                    user.setOnline(true);
                    // kết nối tới RMI
                    serverRMI = (RMIServerInterface) Naming.lookup("rmi://" + serverName + ":" + rmiPort + "/" + rmiField);
                    serverRMI.addRMIClient(user);
                    serverRMI.addOnlineUser(user);
                }
                if (user.isLoggin() && user.isOnline()) {
                    onlineUsers = serverRMI.getAllOnlineUsers();
                    System.out.println("online user size " + onlineUsers.size());
                    updateOnlineList(onlineUsers);
                    // get data from server
                    Object obj = objInput.readObject();
                    if (obj instanceof List<?>) {
                        List<?> receivedList = (List<?>) obj;
                        if (!receivedList.isEmpty() && receivedList.get(0) instanceof MessageModel) {
                            messageList = (List<MessageModel>) obj;
//                            System.out.println(messageList.size());
                            try {
                                updateMessage(messageList);
                            } catch (BadLocationException ex) {
                                Logger.getLogger(ChatView.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException | ClassNotFoundException | NotBoundException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openLogin() {
        loginView = new LoginView();
        loginView.setVisible(true);
        ActionListener loginEvent = (ActionEvent e1) -> {
            try {
                String username = loginView.jUsername.getText();
                String password = loginView.jPassword.getText();
                user = new UserModel(username, password);
                // gửi thông tin đăng nhập cho server kiểm tra
                objOutput.writeObject(user);
            } catch (IOException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        loginView.jPassword.addActionListener(loginEvent);
        loginView.jLogin.addActionListener(loginEvent);
    }

    private void openChat() {
        chatView = new ChatView();
        chatView.setVisible(true);
        chatView.setTitle("Chat App: [" + user.getName() + "]");
        chatView.Title.setText("Welcome back " + user.getName() + " !");
        user.setOnline(true);
        ActionListener chatEvent = (ActionEvent e1) -> {
            messageModel = new MessageModel(user.getName(),
                    chatView.jTextMessage.getText(),
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
            sendMessage(messageModel);
            chatView.jTextMessage.setText("");
        };
        ActionListener logoutEvent = (ActionEvent e1) -> {
            messageModel = new MessageModel("Server",
                    user.getName() + " has logout!",
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
            sendMessage(messageModel);
            chatView.dispose();
            try {
                serverRMI.removeRMIClient(user);
                serverRMI.removeOnlineUser(user);
//                inFromServer.close();
//                outToServer.close();
//                objInput.close();
//                objOutput.close();
//                connection.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        chatView.jTextMessage.addActionListener(chatEvent);
        chatView.jSend.addActionListener(chatEvent);
        chatView.jLogout.addActionListener(logoutEvent);
    }

    public void sendMessage(MessageModel messageModel) {
        try {
            // Gửi thông tin tin nhắn cho server
            objOutput.writeObject(messageModel);
            objOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateMessage(List<MessageModel> messageList) throws BadLocationException {
        for (MessageModel m : messageList) {
            String senderName = m.getName();
            String sentTime = m.getTime();
            String message = m.getMessage();
            String newMessage = "[" + senderName + "] (" + sentTime + "): " + message;
            if (!chatView.jChatArea.getText().contains(newMessage)) {
                // Cập nhật tin nhắn mới trong chatView
                if (senderName.equals(user.getName())) {
                    chatView.appendMessage(newMessage, 2);
                } else if (senderName.equals("Server")) {
                    chatView.appendMessage(newMessage, 1);
                } else {
                    chatView.appendMessage(newMessage, 0);
                }
            }
        }
    }

    private void updateOnlineList(ArrayList<UserModel> onlineUsers) {
        if (!onlineUsers.isEmpty()) {
            chatView.resetUserList();
            for (UserModel online : onlineUsers) {
//                System.out.println("[i] Online user " + online.getUsername());
                chatView.addUserList(online.getName(), online.getImg());
            }
        }
    }

}
