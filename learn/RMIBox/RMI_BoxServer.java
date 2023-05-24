package learn.RMIBox;

import learn.RMIBox.Box;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_BoxServer extends UnicastRemoteObject implements RMI_BoxInterface {

    private static final long serialVersionUID = 7498804040818473161L;

    public RMI_BoxServer() throws RemoteException {
    }

    @Override
    public Box update(Box b) throws RemoteException {
        b.updateSize(0.5, 0.5, 0.5);
        return b;
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(888);
        registry.bind("box", new RMI_BoxServer());
        System.out.println("Server is running and ready for service....");
    }
}
