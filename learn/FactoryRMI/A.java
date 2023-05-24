package learn.FactoryRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface A extends Remote {
	public void m() throws RemoteException;
	public void n() throws RemoteException;
}
