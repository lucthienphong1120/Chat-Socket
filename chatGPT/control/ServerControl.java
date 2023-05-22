package chatGPT.control;

import chatGPT.model.User;
import chatGPT.view.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerControl {

    private serverControlView controlView;
    private chatServerView serverView;
    private ArrayList<User> list;
    private int totalClients = 100;
    private int port = 1234;
    private ServerSocket serverSocket;
    private Socket connection;

    public ServerControl(serverControlView view) {
        this.controlView = view;
        list = new ArrayList<User>();
        list.add(new User("0987654321", "111111"));
        list.add(new User("0988888888", "111111"));
        list.add(new User("0977777777", "111111"));
        list.add(new User("0987575701", "111111"));
        //openServer(6868);
    }

    public void openServer(int port, int totalClients) {
        this.port = port;
        this.totalClients = totalClients;
    }

    public void listening() {
        try {
            serverSocket = new ServerSocket(this.port, this.totalClients);
            this.controlView.showMessage("Server is listening at port " + this.port);
            while (true) {
                connection = serverSocket.accept();
                
                ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
                User user = (User) ois.readObject();
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                if (this.checkLogin(user)) {
                    this.controlView.showMessage(("New client connect at " + connection.getInetAddress().getHostAddress()));
                    writer.print("Success");
                    serverView = new chatServerView();
                } else {
                    this.controlView.showMessage("Login Failed");
                    writer.print("Failed");
                }
                writer.flush();
                writer.close();
                output.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean checkLogin(User user) {
        for (User u : list) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }
}
