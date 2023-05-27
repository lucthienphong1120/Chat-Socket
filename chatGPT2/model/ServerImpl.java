package chatGPT2.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    private static final long serialVersionUID = -6018722832052878923L;

    private ArrayList<UserModel> onlineUsers = new ArrayList<>();

    public ServerImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArrayList<UserModel> getOnlineUsers() throws RemoteException {
        return onlineUsers;
    }

    @Override
    public void updateOnlineUsers(ArrayList<UserModel> onlineUsers) throws RemoteException {
        this.onlineUsers = onlineUsers;
    }

    @Override
    public void addOnlineUsers(UserModel user) throws RemoteException {
        if (!onlineUsers.contains(user)) {
            onlineUsers.add(user);
        } else {
            System.out.println("Error: user adready in list !");
        }
    }

    @Override
    public void removeOnlineUsers(UserModel user) throws RemoteException {
        if (onlineUsers.contains(user)) {
            onlineUsers.remove(user);
        } else {
            System.out.println("Error: user not in list !");
        }
    }

}
