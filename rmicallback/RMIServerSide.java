package rmicallback;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi_implement.AtServerImpl;

public class RMIServerSide {

    public static void main(String[] args) throws MalformedURLException, RemoteException,
            NotBoundException, AlreadyBoundException {

        Registry registry = LocateRegistry.createRegistry(888);
        AtServer self = new AtServerImpl();
        registry.bind("server", self);
        System.out.println("Server is running and ready...");
    }
}
