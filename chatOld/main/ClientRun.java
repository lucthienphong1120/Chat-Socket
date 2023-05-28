package chatOld.main;

import chatOld.control.*;
import chatOld.view.*;

public class ClientRun {

    public static void main(String[] args) {
        loginControlView view = new loginControlView();
        view.setVisible(true);
        ClientControl control = new ClientControl(view);
        control.setup("localhost", 1234);
    }

}
