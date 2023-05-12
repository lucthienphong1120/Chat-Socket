/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package learn;

import java.io.*;

/**
 *
 * @author Phong
 */
public class PrintString {

    public static void main(String[] args) {
        OutputStream os = System.out;
        PrintWriter pw = new PrintWriter(os);
        pw.write("Example: ");
        pw.println("This is an example of PrintWrite instead of using System.out");
        pw.write("Done!\n");
        pw.flush();
    }

}
