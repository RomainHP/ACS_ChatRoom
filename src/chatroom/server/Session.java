package chatroom.server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Session extends Remote {

	abstract void disconnect() throws RemoteException;;

	abstract void sendMessage(String aMsg) throws RemoteException, IOException;

	abstract void receiveMessage(String aMsg) throws IOException, RemoteException;

	abstract String[] getAllUsers() throws RemoteException;
}