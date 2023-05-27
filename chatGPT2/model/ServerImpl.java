package chatGPT2.model;

import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    private static final long serialVersionUID = -6018722832052878923L;

    private ArrayList<UserModel> onlineUsers = new ArrayList<>();
    private ArrayList<ObjectOutputStream> clientOutputs = new ArrayList<>();

    public ServerImpl() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArrayList<UserModel> getOnlineUsers() throws RemoteException {
        return onlineUsers;
    }

    @Override
    public void addOnlineUser(UserModel user) throws RemoteException {
        if (!onlineUsers.contains(user)) {
            onlineUsers.add(user);
        } else {
            System.out.println("Error: user adready in list !");
        }
    }

    @Override
    public void removeOnlineUser(UserModel user) throws RemoteException {
        if (onlineUsers.contains(user)) {
            onlineUsers.remove(user);
        } else {
            System.out.println("Error: user not in list !");
        }
    }

    @Override
    public ArrayList<ObjectOutputStream> getClientOutputs() throws RemoteException {
        return clientOutputs;
    }

    @Override
    public void addClientOutput(ObjectOutputStream objOut) throws RemoteException {
        if (!clientOutputs.contains(objOut)) {
            clientOutputs.add(objOut);
        } else {
            System.out.println("Error: objOut adready in list !");
        }
    }

    @Override
    public void removeClientOutput(ObjectOutputStream objOut) throws RemoteException {
        if (clientOutputs.contains(objOut)) {
            clientOutputs.remove(objOut);
        } else {
            System.out.println("Error: objOut not in list !");
        }
    }

}