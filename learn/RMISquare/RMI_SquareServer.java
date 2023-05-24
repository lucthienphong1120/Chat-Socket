package learn.RMISquare;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_SquareServer extends UnicastRemoteObject implements RMI_SquaredInterface {

    private static final long serialVersionUID = -4002277602339798718L;

    public RMI_SquareServer() throws RemoteException {
    }

    @Override
    public double getSquared(double x) throws RemoteException {
        return x * x;
    }

    @Override
    public double getSquaredRoot(double x) throws RemoteException {
        if (x < 0) {
            return -1;
        }
        return Math.sqrt(x);
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(777);
        registry.bind("squared", new RMI_SquareServer());
        System.out.println("Server is running and ready for service.....");
    }
}
