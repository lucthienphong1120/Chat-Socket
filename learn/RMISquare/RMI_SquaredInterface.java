package learn.RMISquare;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_SquaredInterface extends Remote {

    public double getSquared(double x) throws RemoteException;

    public double getSquaredRoot(double x) throws RemoteException;
}
