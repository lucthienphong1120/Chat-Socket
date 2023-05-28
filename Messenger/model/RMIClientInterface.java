package Messenger.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    public void notifyOnOff(String username, boolean online) throws RemoteException;

}
