package finalLab.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import finalLab.control.ServerControl;

public class OnlineUserImpl extends UnicastRemoteObject implements OnlineUsersInterface {

    private ArrayList<User> onlineUsers;
    private ServerControl control;

    private static final long serialVersionUID = -6018722832052878923L;

    public OnlineUserImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    public OnlineUserImpl(ArrayList<User> onlineUsers) throws RemoteException {
        this.setOnlineUsers(onlineUsers);
    }

    @Override
    public ArrayList<String> getAllOnlineUsers() throws RemoteException {
        ArrayList<String> allAvailUsers = new ArrayList<>();
        for (User u : onlineUsers) {
            if (!allAvailUsers.contains(u.getUsername())) {
                allAvailUsers.add(u.getUsername());
            }
        }
        return allAvailUsers;
    }

    private void setOnlineUsers(ArrayList<User> availableUsers) {
        this.onlineUsers = availableUsers;
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
