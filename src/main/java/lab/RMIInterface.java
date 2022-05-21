package lab;
import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;

interface RMIInterface extends Remote {
	public String echo(Object obj) throws RemoteException;
}
