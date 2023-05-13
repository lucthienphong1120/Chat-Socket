/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Phong
 */
public class PortScanner {

    public static void main(String[] args) {

        String host = "localhost";
        for (int i = 1; i < 65536; i++) {
            Socket connection = null;
            try {
                connection = new Socket(host, i);
                System.out.println("There is a port of server at " + i);
            } catch (IOException e) {
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (IOException e1) {
                }
            }
        }
        System.out.println("End! No more port!");
    }
}
