package chatroom.client;

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Listener extends Remote {

	abstract void setOutput(OutputStream out) throws RemoteException;

	abstract void receiveMessage(Message aMsg) throws RemoteException, IOException;
}