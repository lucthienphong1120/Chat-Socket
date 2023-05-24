package casestudy2.main;

import casestudy2.control.*;
import casestudy2.view.*;

public class ServerRun {

    public static void main(String[] args) {
        ServerView view = new ServerView();
        ServerControl controller = new ServerControl(view);
        controller.openServer(6868);
        controller.listening();
    }
}
