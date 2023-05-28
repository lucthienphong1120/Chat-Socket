package chatGPT2.model;

import chatGPT2.control.ServerControl;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServerInterface extends Remote {

    public ArrayList<String> getAllOnlineUsers() throws RemoteException;

    public void updateRMIClient(UserModel user) throws RemoteException;

    public void updateServerControl(ServerControl control) throws RemoteException;

}
