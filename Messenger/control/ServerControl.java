package Messenger.control;

import Messenger.model.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl {

    // import global variables
    private int serverPort = 1234;
    private int totalClients = 5;
    private String rmiField = "serverRMI";
    private int rmiPort = 1099;
    private ServerSocket serverSocket;
    private Socket connection;
    // import objects
    public RMIServerInterface serverRMI;
    private MessageControl messageControl;
    private ArrayList<UserModel> onlineUsers = new ArrayList<>();

    public ServerControl(int serverPort, int totalClients, String rmiField, int rmiPort) {
        this.serverPort = serverPort;
        this.totalClients = totalClients;
        this.rmiField = rmiField;
        this.rmiPort = rmiPort;
    }

    private void setupFile() {
        messageControl = new MessageControl("./src/message_logs.log");
        messageControl.createFile();
        messageControl.resetFile();
        // Đăng ký sự kiện xoá file khi đóng chương trình
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            messageControl.deleteFile();
        }));
    }

    private void setupRMI() {
        try {
            serverRMI = new RMIServerImpl(onlineUsers);
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            registry.bind(rmiField, serverRMI);
            System.out.println("Dang ky thanh cong Server RMI");
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listening() {
        try {
            // setup anything
            serverSocket = new ServerSocket(serverPort, totalClients);
            System.out.println("[i] Server is listening on port " + serverPort);
            setupFile();
            System.out.println("[i] Log file are created and ready for conversation");
            setupRMI();
            System.out.println("[i] RMI is listening on port " + rmiPort);

            while (true) {
                connection = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(connection);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                serverSocket.close();
                messageControl.deleteFile();
            } catch (IOException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class ClientHandler implements Runnable {

    // import special variables
    private Socket clientSock;
    private static ArrayList<ClientHandler> instances = new ArrayList<>();
    // import global variables
    private OutputStream outToClient;
    private ObjectOutputStream objOutput;
    private InputStream inFromClient;
    private ObjectInputStream objInput;
    private boolean login = false;
    private String serverName = "localhost";
    private String rmiField = "serverRMI";
    private int rmiPort = 1099;
    // import object
    private UserModel user = new UserModel();
    private MessageControl messageControl = new MessageControl("./src/message_logs.log");
    private ArrayList<UserModel> listAllAccounts = user.loadUserData();
    private ArrayList<UserModel> onlineUsers = new ArrayList<>();
    private HashMap<String, RMIClientInterface> listRMIClients = new HashMap<>();
    private List<MessageModel> listMessage;
    private RMIServerInterface serverRMI;

    public ClientHandler(Socket clientSocket) {
        this.clientSock = clientSocket;
        addInstance();
    }

    private synchronized void addInstance() {
        ClientHandler.instances.add(this);
    }

    private synchronized void removeInstance() {
        ClientHandler.instances.remove(this);
    }

    private synchronized void broadcastMessage(Object obj) throws IOException {
        for (ClientHandler client : instances) {
            ObjectOutputStream out = client.getObjectOutputStream();
            out.writeObject(obj);
            out.flush();
        }
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objOutput;
    }

    private void connectRMI() {
        try {
            serverRMI = (RMIServerInterface) Naming.lookup("rmi://" + serverName + ":" + rmiPort + "/" + rmiField);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            connectRMI();
            // get message
            inFromClient = clientSock.getInputStream();
            objInput = new ObjectInputStream(inFromClient);
            // send message
            outToClient = clientSock.getOutputStream();
            objOutput = new ObjectOutputStream(outToClient);
            while (clientSock.isConnected()) {
                // get data from client
                Object obj = objInput.readObject();
                if (!login && obj instanceof UserModel) {
                    user = (UserModel) obj;
                    if (checkLogin(user)) {
                        addOnlineUser(user);
                        objOutput.writeObject(user);
                        login = true;
                    } else {
                        objOutput.writeObject(null);
                    }
                }
                if (login && obj instanceof MessageModel) {
                    MessageModel messageModel = (MessageModel) obj;
                    System.out.println(messageModel.getName() + " " + messageModel.getMessage());
                    // write file
                    messageControl.saveMessage(messageModel);
                    if (messageModel.getMessage().contains("logout")
                            && messageModel.getMessage().contains(user.getName())) {
                        break;
                    }
                }
                // send data to client
                if (login) {
                    listMessage = messageControl.loadMessages();
                    if (!listMessage.isEmpty()) {
                        broadcastMessage(listMessage);
                    }
                }

                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            removeInstance();
        }
    }

    private void addOnlineUser(UserModel user) throws RemoteException {
        onlineUsers = serverRMI.getAllOnlineUsers();
        listRMIClients = serverRMI.getListRMIClients();

        for (UserModel onlineUser : onlineUsers) {
            String username = onlineUser.getUsername();
            // thông báo cho mọi người khác về tôi
//            if (!username.equals(user.getUsername())) {
            RMIClientInterface clientRMI = listRMIClients.get(username);
            clientRMI.notifyOnOff(user.getUsername(), true);
//            }
        }
    }

    public boolean checkLogin(UserModel user) throws RemoteException {
        // nếu user đã login rồi thì không cho đăng nhập nữa
        if (checkAlreadyLogin(user)) {
            return false;
        }
        for (UserModel account : listAllAccounts) {
            if (user.getUsername().equals(account.getUsername())
                    && user.getPassword().equals(account.getPassword())) {
                // thiết lập thông tin cho user
                user.setName(account.getName());
                user.setImg(account.getImg());
                return true;
            }
        }
        return false;
    }

    private boolean checkAlreadyLogin(UserModel user) throws RemoteException {
        onlineUsers = serverRMI.getAllOnlineUsers();
        for (UserModel onlineUser : onlineUsers) {
            if (onlineUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

}
