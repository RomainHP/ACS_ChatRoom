package chatroom.server;

import chatroom.client.message.Message;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Session extends Remote {

    abstract void disconnect() throws RemoteException, IOException;

	abstract void sendMessage(Message aMsg) throws RemoteException, IOException;

    abstract void receiveMessage(Message aMsg) throws IOException, RemoteException;

    abstract String[] getAllUsers() throws RemoteException;
}
