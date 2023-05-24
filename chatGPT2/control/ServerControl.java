package chatGPT2.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl {
    // import global variables
    private int serverPort;
    private int totalClients;
    private int rmiPort;
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

    public ClientHandler(Socket clientSocket) {
        this.clientSock = clientSocket;
    }

    @Override
    public void run() {
        try {
            // get message
            InputStream inFromClient = clientSock.getInputStream();
            DataInputStream in = new DataInputStream(inFromClient);
            System.out.println(in.readUTF());
            // send message
            OutputStream outToClient = clientSock.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToClient);
            out.writeUTF("[Server] Thank you for connecting to " + clientSock.getLocalSocketAddress());
            
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
