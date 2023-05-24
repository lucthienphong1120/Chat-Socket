package learn.RMISample;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AtClientImpl extends UnicastRemoteObject implements AtClient {

    private static final long serialVersionUID = 2122450395676459064L;

    public AtClientImpl() throws RemoteException {

    }

    @Override
    public void callClientMethod(String msg) throws RemoteException {
        System.out.println("Client receives the message: " + msg);
    }

}
