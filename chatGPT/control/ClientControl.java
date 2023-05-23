package chatGPT.control;

import chatGPT.model.*;
import chatGPT.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientControl {

    private loginControlView loginView;
    private chatClientView clientView;
    private int state;
    private String serverName = "localhost";
    private int port = 1234;
    private Socket connection;
    private DataInputStream input;
    private ObjectOutputStream output;

    public ClientControl(loginControlView view) {
        this.state = UserState.NOT_LOGIN;
        this.loginView = view;
    }

    public class LoginListener implements ActionListener {

        private ClientControl control;

        public LoginListener(ClientControl control) {
            this.control = control;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //Lấy thông tin đăng nhập ở loginControlView
            User model = control.loginView.getUserInput();
            if (state == UserState.NOT_LOGIN) {
                // check validator from client input
                if (Validator.checkValid(model) == Constant.VALID) {
                    try {
                        connection = new Socket(serverName, port);
                        output = new ObjectOutputStream(connection.getOutputStream());
                        output.writeObject(model);
                        output.flush();
                        input = new DataInputStream(connection.getInputStream());

                        boolean response = input.readBoolean();
                        System.out.println(response);
                        if (response) {
                            control.loginView.showMessage(true, "Login successfully!");
                            clientView = new chatClientView();
                            loginView.dispose();
                            state = UserState.CONNECTED;
                        } else {
                            control.loginView.showMessage(false, "Invalid username and/or password!");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    control.loginView.showMessage(false, "You got a validation error!");
                }
            }
        }
    }
}
