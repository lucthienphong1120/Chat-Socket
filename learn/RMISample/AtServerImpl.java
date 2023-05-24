package learn.RMISample;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AtServerImpl extends UnicastRemoteObject implements AtServer {

    private AtClient client;
    private static final long serialVersionUID = 2957089344056749582L;

    public AtServerImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setClient(AtClient client) throws RemoteException {
        this.client = client;
    }

    @Override
    public void callback() throws RemoteException {
        if (client != null) {
            for (int i = 0; i < 10; i++) {
                client.callClientMethod("" + i);
            }
        }
    }
}
