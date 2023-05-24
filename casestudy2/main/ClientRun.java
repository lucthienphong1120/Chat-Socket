package casestudy2.main;

import casestudy2.control.*;
import casestudy2.view.*;

public class ClientRun {

    public static void main(String[] args) {
        ClientView view = new ClientView();
        ClientControl control = new ClientControl(view);
        view.setVisible(true);
    }

}
