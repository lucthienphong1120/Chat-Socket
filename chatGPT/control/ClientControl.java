package chatGPT.control;

import chatGPT.model.*;
import chatGPT.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientControl {

    private loginClientView view;
    private String serverHost = "localhost";
    private int serverPort = 1234;

    public ClientControl(loginClientView view) {
        this.view = view;
    }

    public class LoginListener implements ActionListener {

        private ClientControl control;

        public LoginListener(ClientControl control) {
            this.control = control;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //Lấy thông tin đăng nhập ở loginClientView
            User model = control.view.getUserInput();
            // check validator from client input
            if (Constant.VALID.equals(Validator.checkValid(model))) {
                try (Socket socket = new Socket(serverHost, serverPort)) {
                    OutputStream out = socket.getOutputStream();
                    ObjectOutputStream obj = new ObjectOutputStream(out);
                    obj.writeObject(model);

                    //Nhan ket qua tu Server
                    InputStream in = socket.getInputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                    String response = bf.readLine();
                    if (response.equals("Success")) {
                        control.view.showMessage(true, "Login successfully!");
                    } else {
                        control.view.showMessage(false, "Invalid username and/or password!");
                    }
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
                control.view.showMessage(false, "You got a validation error!");
            }
        }
    }
}
