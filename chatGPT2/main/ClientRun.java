package chatGPT2.main;

import chatGPT2.control.*;

public class ClientRun {
    public static void main(String[] args) {
        ClientControl control = new ClientControl("localhost", 1234, "server", 1099);
        control.connecting();
    }
}