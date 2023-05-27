package learn.MultiThreadSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThreadServer {

    public static void main(String[] args) {
        int port = 1234;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // Tạo một luồng xử lý riêng cho mỗi client bằng Thread Runnable
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class ClientHandler implements Runnable { // allows instances of it to be executed by a thread

    private Socket clientSock; // Socket object representing the client connection

    public ClientHandler(Socket clientSocket) {
        this.clientSock = clientSocket;
    }

    @Override
    public void run() {
        // When a client connects, a new Thread is created as the Runnable and started
        // This allows multiple clients to be served concurrently
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
            out.flush();
//            clientSock.close();
        } catch (IOException ex) {
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
