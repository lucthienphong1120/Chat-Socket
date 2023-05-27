package finalLab.main;

import finalLab.control.ClientControl;
import finalLab.view.ClientView;

public class ClientRun {

    public static void main(String[] args) {
        ClientView view = new ClientView();
        ClientControl control = new ClientControl(view);
        view.setVisible(true);
    }

}
