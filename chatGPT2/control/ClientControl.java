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
                    // nhận kết quả từ server
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
                    serverRMI
                            = (RMIServerInterface) Naming.lookup("rmi://" + serverName + ":" + rmiPort + "/" + rmiField);
                    serverRMI.updateRMIClient(user);
                    ArrayList<UserModel> allOtherUsers = serverRMI.getAllOnlineUsers();
                    if (!allOtherUsers.isEmpty()) {
                        for (UserModel otherUser : allOtherUsers) {
                            if (!otherUser.getUsername().equals(user.getUsername())) {
                                System.out.println("[i] Online user " + otherUser.getUsername());
                            }
                        }
                    } else {
                        System.out.println("[i] No one in the room!");
                    }
                }
                if (user.isLoggin() && user.isOnline()) {
                    // get data from server
                    Object obj = objInput.readObject();
                    if (obj instanceof List<?>) {
                        List<?> receivedList = (List<?>) obj;
                        if (!receivedList.isEmpty() && receivedList.get(0) instanceof MessageModel) {
                            messageList = (List<MessageModel>) obj;
                            System.out.println(messageList.size());
                            updateMessage(messageList);
                        }
                    }
                }

                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException | ClassNotFoundException | NotBoundException ex) {
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
        ActionListener e = (ActionEvent e1) -> {
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
        loginView.jPassword.addActionListener(e);
        loginView.jLogin.addActionListener(e);
    }

    private void openChat() {
        chatView = new ChatView();
        chatView.setVisible(true);
        chatView.Title.setText("Welcome back " + user.getName());
        user.setOnline(true);
        ActionListener e = (ActionEvent e1) -> {
            messageModel = new MessageModel(user.getName(),
                    chatView.jTextMessage.getText(),
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
            sendMessage(messageModel);
            chatView.jTextMessage.setText("");
        };
        chatView.jTextMessage.addActionListener(e);
        chatView.jSend.addActionListener(e);
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

    private void updateMessage(List<MessageModel> messageList) {
        for (MessageModel m : messageList) {
            String senderName = m.getName();
            String sentTime = m.getTime();
            String message = m.getMessage();
            String newMessage = "[" + senderName + "] (" + sentTime + "): " + message + "\n";
            if (!chatView.jTextArea.getText().contains(newMessage)) {
                // Cập nhật tin nhắn mới trong JTextArea
                chatView.jTextArea.append(newMessage);
                // Cuộn xuống cuối tin nhắn mới
                chatView.jTextArea.setCaretPosition(chatView.jTextArea.getDocument().getLength());
            }
        }
    }

}
