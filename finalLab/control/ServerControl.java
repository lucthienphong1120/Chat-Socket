package finalLab.control;

import finalLab.model.*;
import finalLab.view.ServerView;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

public class ServerControl {

    private ArrayList<User> listActiveAccounts;
    private ArrayList<User> availableAccounts;
    public AvailableUsersInterface availUsers;
    private HashMap<String, ClientNotificationInterface> listRMIClients;

    private int serverPort;
    private ServerSocket myServer;
    private Socket clientSocket;
    private ServerView view;

    public ServerControl(ServerView view) {
        this.view = view;
        listActiveAccounts = new ArrayList<>();
        availableAccounts = new ArrayList<>();
        listRMIClients = new HashMap<>();
        listActiveAccounts = ServerDBControl.getAllUsers();
        try {
            availUsers = new AvailableUserImpl(availableAccounts);
            availUsers.updateServerControl(this);
            Registry registry = LocateRegistry.createRegistry(789);
            registry.bind("availUsers", availUsers);
            System.out.println("Dang ky thanh cong availUsers");
            //openServer(6868);
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int checkAlreadyLogin(User user) {
        int index = 0;
        for (User u : this.availableAccounts) {
            if (u.equals(user)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private void addAvailableUser(User user) {
        int index = this.checkAlreadyLogin(user);
        if (index == -1) {
            this.availableAccounts.add(user);
            //Cap nhat status trong listActiveAccounts            
            for (int i = 0; i < listActiveAccounts.size(); i++) {
                if (listActiveAccounts.get(i).equals(user)) {
                    user.setStatus("on");
                    listActiveAccounts.set(i, user);
                    break;
                }
            }
            for (int i = 0; i < this.availableAccounts.size(); i++) {
                if (!this.availableAccounts.get(i).
                        getUsername().equals(user.getUsername())) {
                    ClientNotificationInterface client = this.listRMIClients.get(this.availableAccounts.get(i).
                            getUsername());
                    try {
                        client.notifyOnOff(user.getUsername(), true);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void addRMIClientInterface(User user) {
        try {
            ClientNotificationInterface client
                    = (ClientNotificationInterface) Naming.lookup("rmi://localhost:"
                            + user.getPort() + "/" + user.getUsername());
            this.listRMIClients.put(user.getUsername(), client);
            System.out.println(this.listRMIClients.size());
        } catch (Exception e) {
            e.printStackTrace();
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
                User user = (User) obj;
                System.out.println(obj.toString());

                OutputStream output = socketFromClient.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                if (this.checkLogin(user)) {
                    user.setStatus("on");
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
        if (checkAlreadyLogin(user) != -1)//neu user da dang nhap roi thi khong cho dang nhap lan nua
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
