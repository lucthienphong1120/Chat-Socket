package learn.MultiThreadSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientTest {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            // send message
            while (true) {
                OutputStream outToServer = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                out.writeUTF("[Client] Hello from " + socket.getLocalSocketAddress());
                // get message
                InputStream inFromServer = socket.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                System.out.println(in.readUTF());
            }
//            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
