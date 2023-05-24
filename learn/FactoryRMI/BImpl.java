package learn.FactoryRMI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class BImpl extends UnicastRemoteObject implements B {

	private static final long serialVersionUID = -6944077284336605304L;

	public BImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void p() throws RemoteException {
		System.out.println("Phuong thuc p cua lop BImpl");

	}

	@Override
	public void q() throws RemoteException {
		System.out.println("Phuong thuc q cua lop BImpl");

	}

}
