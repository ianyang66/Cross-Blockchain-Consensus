package lab;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client implements RMIInterface {
	
	public String echo(Object input) throws RemoteException {
     	return "Echoing: " + input.toString();
     	}
	
	private static RMIInterface look_up;

	public static void main(String[] args)
		throws MalformedURLException, RemoteException, NotBoundException {

		look_up = (RMIInterface) Naming.lookup("rmi://127.0.0.1:11099/RMIInterface");
		System.out.println("Calling Echo... ");
		try {
		String response = look_up.echo("Let's use a string here. ");
		System.out.println(response);
		} catch (Exception e) {}
		

	}

}
