package chatGPT.control;

import chatGPT.model.*;
import chatGPT.view.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientControl {

    private loginControlView loginView;
    private chatClientView clientView;
    Message client = new Message("Client", "secret");
    private String serverName = "localhost";
    private int port = 1234;
    private int state;
    private Socket connection;

    public ClientControl() {
    }

    public ClientControl(loginControlView view) {
        this.state = UserState.NOT_LOGIN;
        this.loginView = view;
    }

    public void setup(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }

    public void login() {
        if (this.state == UserState.NOT_LOGIN) {
            //Lấy thông tin đăng nhập ở loginControlView
            User model = loginView.getUserInput();
            // check validator from client input
            if (Validator.checkValid(model) == Constant.VALID) {
                connecting(model);
            } else {
                loginView.showMessage(false, "You got a validation error!");
            }
        }
    }

    public void connecting(User model) {
        try {
            connection = new Socket(serverName, port);
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            output.writeObject(model);
            output.flush();
            while (this.state == UserState.NOT_LOGIN) {
                DataInputStream input = new DataInputStream(connection.getInputStream());
                boolean response = input.readBoolean();
                if (response) {
                    loginView.showMessage(true, "Login successfully!");
                    loginView.dispose();
                    this.state = UserState.CONNECTED;
                } else {
                    loginView.showMessage(false, "Invalid username and/or password!");
                }
            }
            if (this.state == UserState.CONNECTED) {
                System.out.println("1 times");
                clientView = new chatClientView(connection, client);
//                clientView.chatting();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
