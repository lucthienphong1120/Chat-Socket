package chatGPT2.control;

import chatGPT2.model.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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

    public ServerControl(int serverPort, int totalClients) {
        this.serverPort = serverPort;
        this.totalClients = totalClients;
    }

    public void listening() {
        try {
            serverSocket = new ServerSocket(serverPort, totalClients);
            System.out.println("Server is listening on port " + serverPort);

            while (true) {
                connection = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(connection);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class ClientHandler implements Runnable {

    private Socket clientSock;
    private int clientState = 0;

    public ClientHandler(Socket clientSocket) {
        this.clientSock = clientSocket;
    }

    @Override
    public void run() {
        try {
            // get message
            InputStream inFromClient = clientSock.getInputStream();
            ObjectInputStream in = new ObjectInputStream(inFromClient);
            // send message
            OutputStream outToClient = clientSock.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outToClient);
            while (true) {
                User user = (User) in.readObject();
                System.out.println(user.getUsername() + " " + user.getPassword());

                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                clientSock.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
