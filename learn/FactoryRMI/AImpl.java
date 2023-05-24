package learn.FactoryRMI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class AImpl extends UnicastRemoteObject implements A {

	private static final long serialVersionUID = -7326638154088219572L;

	public AImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void m() throws RemoteException {
		System.out.println("Phuong thuc m cua lop AImpl");

	}

	@Override
	public void n() throws RemoteException {
		System.out.println("Phuong thuc n cua lop AImpl");

	}

}
