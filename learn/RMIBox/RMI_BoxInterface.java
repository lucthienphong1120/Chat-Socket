package learn.RMIBox;

import learn.RMIBox.Box;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_BoxInterface extends Remote {
	public Box update(Box b) throws RemoteException;
}
