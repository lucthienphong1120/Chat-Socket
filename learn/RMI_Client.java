package learn;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI_Client {

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        RMI_SquaredInterface ri = (RMI_SquaredInterface) Naming.lookup("rmi://localhost:777/squared");
        double y = ri.getSquared(8.0);
        double z = ri.getSquaredRoot(25);
        System.out.println("8*8 = " + y + " sqrt(25) = " + z);

    }

}
