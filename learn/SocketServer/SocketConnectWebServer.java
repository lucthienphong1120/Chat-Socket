/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn.SocketServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class SocketConnectWebServer {

    public static void main(String[] args) {
        String hostname = "www.javatutorial.com";
        int port = 443;
        try {
            Socket connect = new Socket(hostname, port);
            InetAddress raddr = connect.getInetAddress();
            InetAddress laddr = connect.getLocalAddress();
            System.out.println("Connected to " + raddr + " from " + laddr);
            connect.close();
        } catch (IOException ex) {
            System.out.println("Can't connect to " + hostname);
            Logger.getLogger(SocketConnectWebServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
