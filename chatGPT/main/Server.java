package chatGPT.main;

import chatGPT.view.*;

public class Server {

    public static void main(String[] args) {
        chatServerView myServer = new chatServerView();
        myServer.startRunning();
    }
}
