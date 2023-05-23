package learn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThreadServer implements Runnable { // allows instances of it to be executed by a thread

    Socket clientSock; // Socket object representing the client connection

    MultiThreadServer(Socket clientSock) {
        this.clientSock = clientSock;
    }

    public static void main(String args[]) {
        try {
            ServerSocket serverSock = new ServerSocket(1234);
            System.out.println("Listening");
            while (true) {
                Socket sock = serverSock.accept();
                System.out.println("A client connected");
                new Thread(new MultiThreadServer(sock)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            // When a client connects, a new Thread is created as the Runnable and started
            // This allows multiple clients to be served concurrently

            // get message
            InputStream inFromClient = clientSock.getInputStream();
            DataInputStream in = new DataInputStream(inFromClient);
            System.out.println(in.readUTF());
            // send message
            OutputStream outToClient = clientSock.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToClient);
            out.writeUTF("[Server] Thank you for connecting to " + clientSock.getLocalSocketAddress());
//            clientSock.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
