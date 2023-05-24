package lab3.control;

import lab3.model.*;
import lab3.view.*;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl {

    private ArrayList<User> listActiveAccounts;
    private ArrayList<User> availableAccounts;
    public AvailableUsersInterface availUsers;

    private int serverPort;
    private ServerSocket myServer;
    private Socket clientSocket;
    private ServerView view;

    public ServerControl(ServerView view) {
        this.view = view;
        listActiveAccounts = new ArrayList<User>();
        availableAccounts = new ArrayList<User>();
        listActiveAccounts.add(new User("0987654321", "111111"));
        listActiveAccounts.add(new User("0988888888", "111111"));
        listActiveAccounts.add(new User("0977777777", "111111"));
        listActiveAccounts.add(new User("0987575701", "111111"));
        try {
            availUsers = new AvailableUserImpl(availableAccounts);
            Registry registry = LocateRegistry.createRegistry(789);
            registry.bind("availUsers", availUsers);
            System.out.println("Dang ky thanh cong availUsers");
            //openServer(6868);
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean checkAlreadyLogin(User user) {
        boolean found = false;
        for (User u : this.availableAccounts) {
            if (u.equals(user)) {
                found = true;
                break;
            }
        }
        return found;
    }
    
    private void addAvailableUser(User user) {
        boolean found = this.checkAlreadyLogin(user);
        if (!found) {
            this.availableAccounts.add(user);
        }
    }

    public void openServer(int port) {
        this.serverPort = port;
    }

    public void listening() {
        try (ServerSocket serverSocket = new ServerSocket(this.serverPort)) {
            System.out.println("Server is listening at the port: " + this.serverPort);
            while (true) {
                Socket socketFromClient = serverSocket.accept();

                ObjectInputStream ois = new ObjectInputStream(socketFromClient.getInputStream());
                Object obj = (Object) ois.readObject();
                if (obj instanceof Serializable) {
                    Serializable s = (Serializable) obj;
                    System.out.println(s.toString());
                }
                User user = (User) obj;
                System.out.println(obj.toString());
                
                OutputStream output = socketFromClient.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                if (this.checkLogin(user)) {
                    this.view.showMessage("New Client Connected");
                    this.addAvailableUser(user);
                    writer.print("Success");
                } else {
                    this.view.showMessage("Failed");
                    writer.print("Failed");
                }

                writer.flush();
                writer.close();
                output.close();
            }

        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean checkLogin(User user) {
        if (checkAlreadyLogin(user))//neu user da dang nhap roi thi khong cho dang nhap lan nua
        {
            return false;
        }
        for (User u : listActiveAccounts) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }
}
