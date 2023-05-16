/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class ClientExtractURL {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.google.com/index.html");
            System.out.println("URL is " + url.toString());
            System.out.println("Protocol is " + url.getProtocol()); // HTTP/HTTPS/TCP/IP/UDP 
            System.out.println("File name is " + url.getFile());
            System.out.println("Host is " + url.getHost());
            System.out.println("Path is " + url.getPath());
            System.out.println("Port is " + url.getPort());
            System.out.println("Default port is " + url.getDefaultPort());
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClientExtractURL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
