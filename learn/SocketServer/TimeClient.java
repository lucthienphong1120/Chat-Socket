/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn.SocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class TimeClient {

    public static void main(String[] args) {
        try {
            String hostname = "localhost";
            int port = 6868;
            Socket socket = new Socket(hostname, port);
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String time = reader.readLine();
            System.out.println(time);
            input.close();
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(TimeClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
