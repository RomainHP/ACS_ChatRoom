package client;

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ListenerImpl extends UnicastRemoteObject implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6990192506189041301L;
	
	private OutputStream out;

	public ListenerImpl(OutputStream out) throws RemoteException{
		this.out = out;
	}

	public void receiveMessage(String aMsg) throws IOException {
		//display on client interface
		out.write(aMsg.getBytes());
	}
}