/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
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
                System.out.println("Connected");
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
            PrintStream pstream = new PrintStream(clientSock.getOutputStream());
            pstream.println("Hello client");
            clientSock.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
