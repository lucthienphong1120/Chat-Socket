package casestudy2.control;

import casestudy2.model.*;
import casestudy2.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientControl {

    private ClientView view;
    private int serverHost;
    private int serverPort;

    public ClientControl(ClientView view) {
        this.view = view;
    }

    public class LoginListener implements ActionListener {

        private ClientControl control;

        public LoginListener(ClientControl control) {
            this.control = control;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //Lấy thông tin đăng nhập ở ClientView
            User model = control.view.getUserInput();
            if (Constant.VALID.equals(Validator.checkValid(model))) {
                String hostname = "localhost";
                int port = 6868;
                try (Socket socket = new Socket(hostname, port)) {
                    OutputStream out = socket.getOutputStream();
                    ObjectOutputStream obj = new ObjectOutputStream(out);
                    obj.writeObject(model);

                    //Nhan ket qua tu Server
                    InputStream in = socket.getInputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                    String response = bf.readLine();
                    control.view.showMessage(response);
                    in.close();
                    bf.close();

                    obj.flush();
                    obj.close();
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Server not found");
                }
            } else {
                control.view.showMessage("Wrong username or password");
            }
        }
    }
}
