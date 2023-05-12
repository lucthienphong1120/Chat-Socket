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
public class ReadLine {

    public static void main(String[] args) {
        InputStreamReader isr = new InputStreamReader(System.in); // get keyboard input
        BufferedReader br = new BufferedReader(isr);
        while (true) {
            try {
                String line = br.readLine();
                if (line.length() == 0) break;
                System.out.println(line);
            } catch (IOException ex) {
                Logger.getLogger(ReadLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
