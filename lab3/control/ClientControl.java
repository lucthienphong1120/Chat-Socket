package lab3.control;

import lab3.model.*;
import lab3.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.rmi.Naming;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            // check validator from client input
            if (Constant.VALID.equals(Validator.checkValid(model))) {
                try {
                    String hostname = "localhost";
                    int port = 6868;
                    Socket socket = new Socket(hostname, port);
                    OutputStream out = socket.getOutputStream();
                    ObjectOutputStream obj = new ObjectOutputStream(out);
                    obj.writeObject(model);

                    //Nhan ket qua tu Server
                    InputStream in = socket.getInputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                    String response = bf.readLine();
                    if (response.equals("Success")) {
                        control.view.showMessage(true, "Login successfully!");
                        System.out.println("User " + model.getUsername() + " logins sucessfully");
                        AvailableUsersInterface availUsers
                                = (AvailableUsersInterface) Naming.lookup("rmi://localhost:789/availUsers");
                        ArrayList<String> allOtherUsers = availUsers.getAllAvailableUsers();
                        for (String name : allOtherUsers) {
                            if (!name.equals(model.getUsername())) {
                                System.out.println("\t Available user " + name);
                            }
                        }
                    } else {
                        control.view.showMessage(false, "Invalid username and/or password!");
                    }
                    in.close();
                    bf.close();

                    obj.flush();
                    obj.close();
                    out.close();
                } catch (IOException | NotBoundException ex) {
                    Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                control.view.showMessage(false, "You got a validation error!");
            }
        }

    }
}
