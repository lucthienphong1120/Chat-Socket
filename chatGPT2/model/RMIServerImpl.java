package chatGPT2.model;

import chatGPT2.control.ServerControl;
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
    private ServerControl control;

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
            RMIClientInterface client = (RMIClientInterface) Naming.lookup("rmi://localhost:" + user.getPort() + "/" + user.getUsername());
            listRMIClients.put(user.getUsername(), client);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateServerControl(ServerControl control) throws RemoteException {
        this.control = control;
    }

}
