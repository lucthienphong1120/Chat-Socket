package learn.RMISquare;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI_SquareClient {

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        RMI_SquaredInterface rmi = (RMI_SquaredInterface) Naming.lookup("rmi://localhost:777/squared");
        double y = rmi.getSquared(8.0);
        double z = rmi.getSquaredRoot(25);
        System.out.println("8 * 8 = " + y);
        System.out.println("sqrt(25) = " + z);

    }

}
