package chatGPT.main;

import chatGPT.control.*;
import chatGPT.view.*;

public class ServerRun {

    public static void main(String[] args) {
        ServerView view = new ServerView();
        ServerControl controller = new ServerControl(view);
        controller.openServer(6868);
        controller.listening();
    }
}
