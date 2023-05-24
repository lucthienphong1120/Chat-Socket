package learn.RMISample;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AtClient extends Remote {

    public void callClientMethod(String msg) throws RemoteException;
}
