package chatGPT2.control;

import chatGPT2.model.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl {

    // import global variables
    private int serverPort = 1234;
    private int totalClients = 5;
    private int rmiPort = 1099;
    private ServerSocket serverSocket;
    private Socket connection;
    // import objects
    MessageControl messageControl = new MessageControl("./src/message_logs.log");

    public ServerControl(int serverPort, int totalClients) {
        this.serverPort = serverPort;
        this.totalClients = totalClients;
    }

    public void listening() {
        try {
            serverSocket = new ServerSocket(serverPort, totalClients);
            System.out.println("Server is listening on port " + serverPort);
            messageControl.createFile();
            messageControl.resetFile();

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

    // import global variables
    private Socket clientSock;
    OutputStream outToClient;
    ObjectOutputStream objOutput;
    InputStream inFromClient;
    ObjectInputStream objInput;
    // import object
    MessageControl messageControl = new MessageControl("./src/message_logs.log");
    List<MessageModel> listMessage;

    public ClientHandler(Socket clientSocket) {
        this.clientSock = clientSocket;
    }

    @Override
    public void run() {
        try {
            // get message
            inFromClient = clientSock.getInputStream();
            objInput = new ObjectInputStream(inFromClient);
            // send message
            outToClient = clientSock.getOutputStream();
            objOutput = new ObjectOutputStream(outToClient);
            while (true) {
                // get data from client
                Object obj = objInput.readObject();
                if (obj instanceof UserModel) {
                    UserModel user = (UserModel) obj;
                    System.out.println(user.getName() + " " + user.getUsername() + " " + user.getPassword());
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
                    objOutput.writeObject(listMessage);
                    objOutput.flush();
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
