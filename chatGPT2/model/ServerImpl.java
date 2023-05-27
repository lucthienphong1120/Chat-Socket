package chatGPT2.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    private ArrayList<UserModel> availableUsers;

    private static final long serialVersionUID = -6018722832052878923L;

    public ServerImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    public ServerImpl(ArrayList<UserModel> availableUsers) throws RemoteException {
        this.availableUsers = availableUsers;
    }

    @Override
    public ArrayList<String> getAllAvailableUsers() throws RemoteException {
        ArrayList<String> allAvailUsers = new ArrayList<>();
        for (UserModel u : availableUsers) {
            if (!allAvailUsers.contains(u.getUsername())) {
                allAvailUsers.add(u.getUsername());
            }
        }
        return allAvailUsers;
    }

}
