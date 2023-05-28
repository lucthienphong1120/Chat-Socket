package chatOld.control;

import chatOld.model.*;
import chatOld.view.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl {

    private serverControlView controlView;
    private chatServerView serverView;
    private ArrayList<User> list;
    private Message server = new Message("Server", "secret");
    private int state;
    private int totalClients = 100;
    private int port = 1234;
    private ServerSocket serverSocket;
    private Socket connection;

    public ServerControl() {
    }

    public ServerControl(serverControlView view) {
        this.state = UserState.NOT_LOGIN;
        this.controlView = view;
        this.loadUserData();
    }

    public void openServer(int port, int totalClients) {
        this.port = port;
        this.totalClients = totalClients;
    }

    private void loadUserData() {
        list = new ArrayList<>();
        list.add(new User("0987654321", "111111"));
        list.add(new User("0988888888", "111111"));
        list.add(new User("0977777777", "111111"));
        list.add(new User("0987575701", "111111"));
    }

    public boolean checkLogin(User user) {
        for (User u : list) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void listening() {
        try {
            serverSocket = new ServerSocket(this.port, this.totalClients);
            this.controlView.showMessage("Server is listening at port " + this.port);
            connection = serverSocket.accept();
            while (this.state == UserState.NOT_LOGIN) {
                ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
                User user = (User) input.readObject();
                DataOutputStream output = new DataOutputStream(connection.getOutputStream());

                if (this.checkLogin(user)) {
                    this.controlView.showMessage(("New client connect at " + connection.getInetAddress().getHostAddress()));
                    output.writeBoolean(true);
                    this.state = UserState.CONNECTED;
                } else {
                    output.writeBoolean(false);
                }
                output.flush();
            }
            if (this.state == UserState.CONNECTED) {
                serverView = new chatServerView(connection, server);
                serverView.sendMessage("hello");
                serverView.chatting();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
