package learn.FactoryRMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientRun {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{
		try {
			Factory factory = (Factory)Naming.lookup("rmi://localhost:789/factory");
			
			A objA = factory.getA();
			B objB = factory.getB();
			C objC = factory.getC();
			
			objA.m();
			objA.n();
			
			objB.p();
			objB.q();
			
			objC.h();
			objC.g();
			
			System.out.println("Client already called methods of A, B, C");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
