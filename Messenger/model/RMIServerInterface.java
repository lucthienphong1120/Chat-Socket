package Messenger.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RMIServerInterface extends Remote {

    public ArrayList<UserModel> getAllOnlineUsers() throws RemoteException;
    
    public HashMap<String, RMIClientInterface> getListRMIClients() throws RemoteException;

    public void updateRMIClient(UserModel user) throws RemoteException;

}
