package chatGPT2.main;

import chatGPT2.control.*;

public class ServerRun {

    public static void main(String[] args) {
        ServerControl controller = new ServerControl(1234, 2);
        controller.listening();
    }
}
