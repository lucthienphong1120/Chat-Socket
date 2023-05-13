package chatGPT.main;

import chatGPT.view.*;

public class Client {

    public static void main(String[] args) {
        chatClientView client;
        client = new chatClientView("127.0.0.1");
        client.startRunning();
    }
}
