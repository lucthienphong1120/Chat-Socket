package chatGPT.main;

import chatGPT.control.*;
import chatGPT.view.*;

public class ClientRun {

    public static void main(String[] args) {
        // Login
        loginClientView view = new loginClientView();
        ClientControl control = new ClientControl(view);
        view.setVisible(true);
        // Chat
        chatClientView client;
        client = new chatClientView("localhost");
        client.startRunning();
    }

}
