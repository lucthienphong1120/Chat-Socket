package chatGPT.main;

import chatGPT.control.*;
import chatGPT.view.*;

public class ClientRun {

    public static void main(String[] args) {
        // Login
        loginControlView view = new loginControlView();
        view.setVisible(true);
        ClientControl control = new ClientControl(view);
        // Chat
//        chatClientView client = new chatClientView();
//        client.startRunning();
    }

}
