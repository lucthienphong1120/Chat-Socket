package Messenger.main;

import Messenger.control.*;

public class ClientRun {
    public static void main(String[] args) {
        ClientControl control = new ClientControl("localhost", 1234, "serverRMI", 1099);
        control.connecting();
    }
}