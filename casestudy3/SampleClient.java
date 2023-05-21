package casestudy3;

import java.rmi.*;
import java.rmi.server.*;

public class SampleClient {

    public static void main(String[] args) {
        try {
            String url = "//localhost/SAMPLE-SERVER";
            SampleServer remoteObject = (SampleServer) Naming.lookup(url);
            System.out.println("Got remote object");
            System.out.println(" 1 + 2 = " + remoteObject.sum(1, 2));
        } catch (RemoteException exc) {
            System.out.println("Error in lookup: " + exc.toString());
        } catch (java.net.MalformedURLException exc) {
            System.out.println("Malformed URL: " + exc.toString());
        } catch (java.rmi.NotBoundException exc) {
            System.out.println("NotBound: " + exc.toString());
        }
    }
}
