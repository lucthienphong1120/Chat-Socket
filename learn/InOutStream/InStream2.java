package learn.InOutStream;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
