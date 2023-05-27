package chatGPT2.model;

import java.io.ObjectOutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {

    public ArrayList<UserModel> getOnlineUsers() throws RemoteException;
    
    public void addOnlineUser(UserModel user) throws RemoteException;
    
    public void removeOnlineUser(UserModel user) throws RemoteException;
    
    public ArrayList<ObjectOutputStream> getClientOutputs() throws RemoteException;
    
    public void addClientOutput(ObjectOutputStream objOut) throws RemoteException;
    
    public void removeClientOutput(ObjectOutputStream objOut) throws RemoteException;

}
