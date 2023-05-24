package learn.FactoryRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface B extends Remote {
	public void p() throws RemoteException;
	public void q() throws RemoteException;
}
