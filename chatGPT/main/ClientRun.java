package chatGPT.main;

import chatGPT.control.*;
import chatGPT.view.*;

public class ClientRun {

    public static void main(String[] args) {
        // Login
        loginControlView view = new loginControlView();
        ClientControl control = new ClientControl(view);
        view.setVisible(true);
        // Chat
//        chatClientView client = new chatClientView();
//        client.startRunning();
    }

}
