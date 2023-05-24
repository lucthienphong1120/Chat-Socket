package learn.FactoryRMI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class CImpl extends UnicastRemoteObject implements C {

	private static final long serialVersionUID = -4287572928520801163L;

	public CImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
	}

		@Override
	public void h() throws RemoteException {
		System.out.println("Phuong thuc h cua lop CImpl");
	}

	@Override
	public void g() throws RemoteException {
		System.out.println("Phuong thuc g cua lop CImpl");
	}

}
