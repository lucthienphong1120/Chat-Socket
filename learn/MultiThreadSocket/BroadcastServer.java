package learn.MultiThreadSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BroadcastServer implements Runnable {

    private ServerSocket serverSocket;
    private int port;

    public BroadcastServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(BroadcastServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        BroadcastServer server = new BroadcastServer(1234);
        server.run();
    }
}

class ClientHandler implements Runnable {

    private Socket clientSock;
    private static ArrayList<ClientHandler> instances = new ArrayList<>();

    public ClientHandler(Socket clientSocket) {
        clientSock = clientSocket;
        //add this to the arraylist, one instance/thread for every connection.
        add();
    }

    private synchronized void add() {
        ClientHandler.instances.add(this);
    }

    private synchronized void remove() {
        ClientHandler.instances.remove(this);
    }

    private synchronized void broadcast(String message) throws IOException {
        for (ClientHandler client : instances) {
            try (DataOutputStream out = new DataOutputStream(client.clientSock.getOutputStream())) {
                out.writeUTF(message);
                out.flush();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        try {
            // get message
            InputStream inFromClient = clientSock.getInputStream();
            DataInputStream in = new DataInputStream(inFromClient);
            String inMessage = in.readUTF();
            System.out.println(inMessage);
            // send message
            OutputStream outToClient = clientSock.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToClient);
            out.writeUTF("[Server] Thank you for connecting to " + clientSock.getLocalSocketAddress());
            broadcast("[Broadcast] There are " + instances.size() + " total clients!");

        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
