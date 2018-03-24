package chatroom.server;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Session extends Remote {

	abstract void disconnect() throws RemoteException;;

	abstract void sendMessage(String aMsg) throws RemoteException;
	
	abstract void receiveMessage(String aMsg) throws IOException, RemoteException;

	abstract List<String> getAllUsers() throws RemoteException;;
}