package learn.FactoryRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FactoryImpl extends UnicastRemoteObject implements Factory {

	private static final long serialVersionUID = 9043895410111688591L;

	public FactoryImpl() throws RemoteException {

	}

	
	@Override
	public A getA() throws RemoteException {
		return new AImpl();
	}

	@Override
	public B getB() throws RemoteException {
		return new BImpl();
	}

	@Override
	public C getC() throws RemoteException {
		return new CImpl();
	}

}
