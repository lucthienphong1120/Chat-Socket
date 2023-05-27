package chatGPT2.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {

    public ArrayList<String> getAllAvailableUsers() throws RemoteException;

}
