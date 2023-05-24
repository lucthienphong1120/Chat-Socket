package learn.RMISample;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AtServer extends Remote {

    public void setClient(AtClient client) throws RemoteException;

    public void callback() throws RemoteException;
}
