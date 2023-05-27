package finalLab.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import finalLab.control.ServerControl;

public class AvailableUserImpl extends UnicastRemoteObject implements AvailableUsersInterface {

    private ArrayList<User> availableUsers;
    private ServerControl control;

    private static final long serialVersionUID = -6018722832052878923L;

    public AvailableUserImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    public AvailableUserImpl(ArrayList<User> availableUsers) throws RemoteException {
        this.setAvailableUsers(availableUsers);
    }

    @Override
    public ArrayList<String> getAllAvailableUsers() throws RemoteException {
        ArrayList<String> allAvailUsers = new ArrayList<String>();
        for (User u : availableUsers) {
            if (!allAvailUsers.contains(u.getUsername())) {
                allAvailUsers.add(u.getUsername());
            }
        }
        return allAvailUsers;
    }

    private void setAvailableUsers(ArrayList<User> availableUsers) {
        this.availableUsers = availableUsers;
    }

    @Override
    public void updateRMIClient(User user) throws RemoteException {
        control.addRMIClientInterface(user);
    }

    @Override
    public void updateServerControl(ServerControl control) throws RemoteException {
        this.control = control;
    }

}
