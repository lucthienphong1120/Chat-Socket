package chatOld.main;

import chatOld.control.*;
import chatOld.view.*;

public class ServerRun {

    public static void main(String[] args) {
        serverControlView view = new serverControlView();
        view.setVisible(true);
        ServerControl controller = new ServerControl(view);
        controller.openServer(1234, 100);
        controller.listening();
    }
}
