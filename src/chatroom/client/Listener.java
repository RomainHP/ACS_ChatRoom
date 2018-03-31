package chatroom.client;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import chatroom.client.ui.Display;

public interface Listener extends Remote {

	public abstract void setOutput(Display out) throws RemoteException;

	public abstract void receiveMessage(Message aMsg) throws RemoteException, IOException;
}