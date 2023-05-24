package learn.RMIBox;

import learn.RMIBox.Box;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI_BoxClient {

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        RMI_BoxInterface rmi = (RMI_BoxInterface) Naming.lookup("rmi://localhost:888/box");
        Box box = new Box();
        box.setD(0);
        box.setH(0);
        box.setW(0);
        box = rmi.update(box);
        System.out.println(box.getD() + " " + box.getH() + " " + box.getW());
    }
}
