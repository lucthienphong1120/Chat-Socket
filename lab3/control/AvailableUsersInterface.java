package lab3.control;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface AvailableUsersInterface extends Remote {
	public ArrayList<String> getAllAvailableUsers() throws RemoteException;
}
