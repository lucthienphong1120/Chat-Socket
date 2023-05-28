package finalLab.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientNoticeInterface extends Remote {

    public void notifyOnOff(String username, boolean status) throws RemoteException;

}
