package chatGPT2.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClientImpl extends UnicastRemoteObject implements RMIClientInterface {

    private static final long serialVersionUID = -2541852276741164148L;

    public RMIClientImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void notifyOnOff(String username, boolean online) throws RemoteException {
        System.out.println("[i]" + username + " is " + (online ? "on" : "off"));
    }

}
