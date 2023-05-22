package chatGPT.main;

import chatGPT.control.*;
import chatGPT.view.*;

public class ServerRun {

    public static void main(String[] args) {
        // Login
        loginServerView view = new loginServerView();
        view.setVisible(true);
//        ServerControl controller = new ServerControl(view);
//        controller.openServer(1234);
//        controller.listening();
        // Chat
//        chatServerView myServer = new chatServerView();
//        myServer.startRunning();
    }
}
