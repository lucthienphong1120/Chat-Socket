package chatGPT2.control;

import chatGPT2.model.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl {

    // import global variables
    private int serverPort = 1234;
    private int totalClients = 5;
    private String rmiField = "server";
    private int rmiPort = 1099;
    private ServerSocket serverSocket;
    private Socket connection;
    // import objects
    MessageControl messageControl;

    public ServerControl(int serverPort, int totalClients) {
        this.serverPort = serverPort;
        this.totalClients = totalClients;
    }

    private void setupFile() {
        messageControl = new MessageControl("./src/message_logs.log");
        messageControl.createFile();
        messageControl.resetFile();
        // Đăng ký sự kiện xoá file khi đóng chương trình
        Runtime.getRuntime().addShutdownHook(new Thread(messageControl::deleteFile));
    }

    private void setupRMI() {
        try {
            ServerInterface serverRMI = new ServerImpl();
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            registry.bind(rmiField, serverRMI);
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
    OutputStream outToClient;
    ObjectOutputStream objOutput;
    InputStream inFromClient;
    ObjectInputStream objInput;
    boolean login = false;
    // import object
    MessageControl messageControl = new MessageControl("./src/message_logs.log");
    List<MessageModel> listMessage;
    ServerInterface serverRMI;

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
            serverRMI = (ServerInterface) Naming.lookup("rmi://localhost:1099/server");
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
                    UserModel user = (UserModel) obj;
                    System.out.println(user.getName() + " " + user.getUsername() + " " + user.getPassword());
                    serverRMI.addOnlineUser(user);
                    login = true;
                }
                if (obj instanceof MessageModel) {
                    MessageModel messageModel = (MessageModel) obj;
                    System.out.println(messageModel.getName() + " " + messageModel.getMessage());
                    // write file
                    messageControl.saveMessage(messageModel);
                }
                // send data to client
                listMessage = messageControl.loadMessages();
                if (!listMessage.isEmpty()) {
//                    objOutput.writeObject(listMessage);
//                    objOutput.flush();
                    broadcastMessage(listMessage);
                }

                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inFromClient.close();
                outToClient.close();
                objInput.close();
                objOutput.close();
                clientSock.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
