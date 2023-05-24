package learn.ThreadHandle;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
