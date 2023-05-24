package learn.FactoryRMI;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRun {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		try {
			Registry registry = LocateRegistry.createRegistry(789);
			registry.bind("factory", new FactoryImpl());
			System.out.println("Server is initialized and running....");
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
