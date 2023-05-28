package learn.MultiThreadSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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

                Thread t = new Thread(new MTSClientHandler(clientSocket));
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

class BSClientHandler implements Runnable {

    private Socket clientSock;
    private static ArrayList<BSClientHandler> instances = new ArrayList<>();

    InputStream inFromClient;
    DataInputStream in;
    OutputStream outToClient;
    DataOutputStream out;

    public BSClientHandler(Socket clientSocket) {
        clientSock = clientSocket;
        //add this to the arraylist, one instance/thread for every connection.
        addInstance();
    }

    private synchronized void addInstance() {
        BSClientHandler.instances.add(this);
    }

    private synchronized void removeInstance() {
        BSClientHandler.instances.remove(this);
    }

    public DataOutputStream getDataOutputStream() {
        return out;
    }

    private synchronized void broadcastMessage(String message) throws IOException {
        for (BSClientHandler client : instances) {
            DataOutputStream out = client.getDataOutputStream();
            out.writeUTF(message);
        }
    }

    @Override
    public void run() {
        try {
            // get message
            inFromClient = clientSock.getInputStream();
            in = new DataInputStream(inFromClient);
            String inMessage = in.readUTF();
            System.out.println(inMessage);
            // send message
            outToClient = clientSock.getOutputStream();
            out = new DataOutputStream(outToClient);
            out.writeUTF("[Server] Thank you for connecting to " + clientSock.getLocalSocketAddress());
            broadcastMessage("[Broadcast] There are " + instances.size() + " total clients!");
        } catch (IOException ex) {
            Logger.getLogger(BSClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
