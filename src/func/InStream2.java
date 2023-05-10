/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package func;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class InStream2 {

    public static void main(String[] args) {
        InputStream is = System.in; // get keyboard input
        while (true) {
            try {
                int num = is.available();
                if(num > 0){
                    byte[] b = new byte[num];
                    int next = is.read(b);
                    String s = new String(b);
                    if(next == -1 || s.trim().equalsIgnoreCase("q")) break;
                    System.out.println(s);
                }
                else{
                    //System.out.println(".");
                }
            } catch (IOException ex) {
                Logger.getLogger(InStream2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
