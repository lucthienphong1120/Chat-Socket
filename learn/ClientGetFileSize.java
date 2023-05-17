/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class ClientGetFileSize {

    public static void main(String[] argv) {
        try {
            int size;
            URL url = new URL("https://www.google.com/index.php");
            URLConnection connect = url.openConnection();
            size = connect.getContentLength();
            if (size < 0) {
                System.out.println("Could not determine file size.");
            } else {
                System.out.println("The size of file is " + size + " bytes");
            }
            connect.getInputStream().close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClientGetFileSize.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientGetFileSize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
