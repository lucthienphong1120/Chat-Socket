package learn.FactoryRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface C extends Remote {
	public void h() throws RemoteException;
	public void g() throws RemoteException;
}
