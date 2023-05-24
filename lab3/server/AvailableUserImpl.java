package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AvailableUserImpl extends UnicastRemoteObject implements AvailableUsersInterface {
	
	private ArrayList<User> availableUsers;

	public void setAvailableUsers(ArrayList<User> availableUsers) {
		this.availableUsers = availableUsers;
	}

	private static final long serialVersionUID = -6018722832052878923L;

	public AvailableUserImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
	}
	
	public AvailableUserImpl(ArrayList<User> availableUsers) throws RemoteException {
		this.setAvailableUsers(availableUsers);
	}

	public ArrayList<String> getAllAvailableUsers() throws RemoteException{
		ArrayList<String> allAvailUsers = new ArrayList<String>();
		for(User u : availableUsers) {
			if(!allAvailUsers.contains(u.getUsername())) {
				allAvailUsers.add(u.getUsername());
			}
		}
		return allAvailUsers;
	}

}
