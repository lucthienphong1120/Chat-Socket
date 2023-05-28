package Messenger.model;

import Messenger.control.ServerControl;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServerImpl extends UnicastRemoteObject implements RMIServerInterface {

    private static final long serialVersionUID = -6018722832052878923L;

    private ArrayList<UserModel> onlineUsers;
    private HashMap<String, RMIClientInterface> listRMIClients = new HashMap<>();

    public RMIServerImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    public RMIServerImpl(ArrayList<UserModel> onlineUsers) throws RemoteException {
        this.onlineUsers = onlineUsers;
    }

    @Override
    public ArrayList<UserModel> getAllOnlineUsers() throws RemoteException {
        return onlineUsers;
    }
    
    @Override
    public HashMap<String, RMIClientInterface> getListRMIClients() throws RemoteException {
        return listRMIClients;
    }

    @Override
    public void updateRMIClient(UserModel user) throws RemoteException {
        this.onlineUsers.add(user);
        try {
            RMIClientInterface clientRMI = (RMIClientInterface) Naming.lookup("rmi://localhost:" + user.getPort() + "/" + user.getUsername());
            listRMIClients.put(user.getUsername(), clientRMI);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
