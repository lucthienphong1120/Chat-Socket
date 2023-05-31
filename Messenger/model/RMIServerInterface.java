package Messenger.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RMIServerInterface extends Remote {

    public ArrayList<UserModel> getAllOnlineUsers() throws RemoteException;
    
    public HashMap<String, RMIClientInterface> getListRMIClients() throws RemoteException;

    public void addRMIClient(UserModel user) throws RemoteException;
    
    public void addOnlineUser(UserModel user) throws RemoteException;

    public void removeRMIClient(UserModel user) throws RemoteException;
    
    public void removeOnlineUser(UserModel user) throws RemoteException;

}
