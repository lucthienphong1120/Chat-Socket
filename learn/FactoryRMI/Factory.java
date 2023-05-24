package learn.FactoryRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Factory extends Remote {
	public A getA() throws RemoteException;
	public B getB() throws RemoteException;
	public C getC() throws RemoteException;
}
