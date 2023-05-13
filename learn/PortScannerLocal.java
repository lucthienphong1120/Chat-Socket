/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Phong
 */
public class PortScannerLocal {

    public static void main(String[] args) {
        for (int i = 0; i < 65535; i++) {
            try {
                ServerSocket server = new ServerSocket(i);
            } catch (IOException ex) {
                System.out.println("There is a port of server at " + i);
            }
        }
    }
}
