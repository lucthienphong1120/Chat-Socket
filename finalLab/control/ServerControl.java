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

    private ArrayList<User> listAllAccounts = ServerDBControl.getAllUsers();
    private ArrayList<User> onlineAccounts = new ArrayList<>();
    private HashMap<String, ClientNoticeInterface> listRMIClients = new HashMap<>();
    public OnlineUsersInterface serverRMI;

    private int serverPort;
    private ServerSocket myServer;
    private Socket clientSocket;
    private ServerView view;

    public ServerControl(ServerView view) {
        this.view = view;
        try {
            serverRMI = new OnlineUserImpl(onlineAccounts);
            serverRMI.updateServerControl(this);
            Registry registry = LocateRegistry.createRegistry(789);
            registry.bind("serverRMI", serverRMI);
            System.out.println("Dang ky thanh cong serverRMI");
            //openServer(6868);
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkAlreadyLogin(User user) {
        for (User onlineUser : onlineAccounts) {
            if (onlineUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    private void addOnlineUser(User user) throws RemoteException {
        if (!this.checkAlreadyLogin(user)) {
            this.onlineAccounts.add(user);
            //Cap nhat status trong listActiveAccounts            
            for (int i = 0; i < listAllAccounts.size(); i++) {
                if (listAllAccounts.get(i).equals(user)) {
                    user.setStatus("on");
                    listAllAccounts.set(i, user);
                    break;
                }
            }
            for (int i = 0; i < this.onlineAccounts.size(); i++) {
                if (!this.onlineAccounts.get(i).
                        getUsername().equals(user.getUsername())) {
                    ClientNoticeInterface client = this.listRMIClients.get(this.onlineAccounts.get(i).
                            getUsername());
                    client.notifyOnOff(user.getUsername(), true);
                }
            }
        }
    }

    public void addRMIClientInterface(User user) {
        try {
            ClientNoticeInterface client
                    = (ClientNoticeInterface) Naming.lookup("rmi://localhost:"
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
                    this.addOnlineUser(user);
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
        for (User u : listAllAccounts) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }
}
