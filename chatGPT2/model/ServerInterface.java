package chatGPT2.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {

    public ArrayList<UserModel> getOnlineUsers() throws RemoteException;
    
    public void updateOnlineUsers(ArrayList<UserModel> onlineUsers) throws RemoteException;
    
    public void addOnlineUsers(UserModel user) throws RemoteException;
    
    public void removeOnlineUsers(UserModel user) throws RemoteException;

}
