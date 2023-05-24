/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn.SocketServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class SocketGreetingServer extends Thread {

    private final ServerSocket serverSocket;

    public SocketGreetingServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000); // max 10s to close connection
    }

    @Override
    public void run() {
        while (true) {
            try {
                // starting server
                System.out.println("[Host] Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept(); // block to wait request
                System.out.println("[Host] Just connected to " + server.getRemoteSocketAddress());
                // get message
                InputStream inFromClient = server.getInputStream();
                DataInputStream in = new DataInputStream(inFromClient);
                System.out.println(in.readUTF());
                // send message
                OutputStream outToClient = server.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToClient);
                out.writeUTF("[Server] Thank you for connecting to " + server.getLocalSocketAddress());
                // server close
                server.close();
            } catch (SocketTimeoutException ste) {
                System.out.println("[Host] Socket timed out!");
                break;
            } catch (IOException ex) {
                System.out.println("[Host] Connection error!");
                Logger.getLogger(SocketGreetingClient.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
    }

    public static void main(String[] args) {
//        int port = Integer.parseInt(args[0]);
        int port = 12345;
        try {
            Thread t = new SocketGreetingServer(port);
            t.start();
        } catch (IOException ex) {
            Logger.getLogger(SocketGreetingClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
