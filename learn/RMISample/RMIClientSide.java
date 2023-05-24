package learn.RMISample;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClientSide {

    public static void main(String[] args) throws MalformedURLException, RemoteException,
            NotBoundException, AlreadyBoundException {

        AtServer srv = (AtServer) Naming.lookup("rmi://localhost:888/server");
        AtClient self = new AtClientImpl();
        srv.setClient(self);
        srv.callback();
        System.out.println("Client requested and then finished");
    }
}
