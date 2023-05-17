/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class ClientReadWriteFile {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.google.com/robots.txt");
            // read file from url
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            // write file at home project folder
//            BufferedWriter writer = new BufferedWriter(new FileWriter("ClientReadWriteFile.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
//                writer.write(line);
//                writer.newLine();
            }
            reader.close();
//            writer.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClientReadWriteFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientReadWriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
