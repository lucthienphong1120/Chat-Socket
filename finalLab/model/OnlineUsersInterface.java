package finalLab.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import finalLab.control.ServerControl;

public interface OnlineUsersInterface extends Remote {

    public ArrayList<String> getAllOnlineUsers() throws RemoteException;

    public void updateRMIClient(User user) throws RemoteException;

    public void updateServerControl(ServerControl control) throws RemoteException;
}
