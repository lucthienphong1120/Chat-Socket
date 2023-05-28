package chatGPT2.model;

import chatGPT2.control.ServerControl;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RMIServerInterface extends Remote {

    public ArrayList<UserModel> getAllOnlineUsers() throws RemoteException;
    
    public HashMap<String, RMIClientInterface> getListRMIClients() throws RemoteException;

    public void updateRMIClient(UserModel user) throws RemoteException;

    public void updateServerControl(ServerControl control) throws RemoteException;

}
