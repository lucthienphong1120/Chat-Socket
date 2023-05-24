package learn.InetConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

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
