/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class SocketGreetingClient {

    public static void main(String[] args) {
        try {
//            String serverName = args[0];
//            int port = Integer.parseInt(args[1]);
            String serverName = "localhost";
            int port = 12345;
            // open a connection
            System.out.println("[Host] Connectiong to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("[Host] Just connected to "+ client.getRemoteSocketAddress());
            // send message
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("[Client] Hello from " + client.getLocalSocketAddress());
            // get message
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println(in.readUTF());
            // close connection
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketGreetingClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
