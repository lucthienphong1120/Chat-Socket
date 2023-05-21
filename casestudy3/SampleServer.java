package casestudy3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SampleServer extends Remote {

    public int sum(int a, int b) throws RemoteException;
}
