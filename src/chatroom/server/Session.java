package chatroom.server;

import chatroom.client.message.Message;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Session extends Remote {

    public abstract void disconnect() throws RemoteException, IOException;

    public abstract void sendMessage(Message aMsg) throws RemoteException, IOException;

    public abstract void receiveMessage(Message aMsg) throws IOException, RemoteException;

    public abstract String[] getAllUsers() throws RemoteException;
}
