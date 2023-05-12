/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package learn;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class InStream1 {
    public static void main(String[] args) {
        InputStream is = System.in; // get keyboard input
        while(true) {
            try {
                int ch = is.read();
                if (ch == -1 || ch == 'q') break; // EOF or press q
                System.out.print((char)ch);
            } catch (IOException ex) {
                Logger.getLogger(InStream1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
