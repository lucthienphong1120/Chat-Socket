package chatGPT2.model;

import chatGPT2.control.ServerControl;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServerImpl extends UnicastRemoteObject implements RMIServerInterface {

    private static final long serialVersionUID = -6018722832052878923L;

    private ArrayList<UserModel> onlineUsers;
    private ServerControl control;

    public RMIServerImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    public RMIServerImpl(ArrayList<UserModel> onlineUsers) throws RemoteException {
        this.onlineUsers = onlineUsers;
    }

    @Override
    public ArrayList<String> getAllOnlineUsers() throws RemoteException {
        ArrayList<String> allAvailUsers = new ArrayList<>();
        for (UserModel u : onlineUsers) {
            if (!allAvailUsers.contains(u.getUsername())) {
                allAvailUsers.add(u.getUsername());
            }
        }
        return allAvailUsers;
    }

    @Override
    public void updateRMIClient(UserModel user) throws RemoteException {
        this.onlineUsers.add(user);
        control.addRMIClientInterface(user);
    }

    @Override
    public void updateServerControl(ServerControl control) throws RemoteException {
        this.control = control;
    }

}
