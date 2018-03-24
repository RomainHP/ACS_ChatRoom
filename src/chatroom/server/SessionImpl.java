package chatroom.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import chatroom.client.Client;
import chatroom.client.Listener;

public class SessionImpl extends UnicastRemoteObject implements Session {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9184666239790872778L;
	private String nickname;
	private ChatRoom chatroom;
	private Listener listener;

	public SessionImpl(ChatRoom aChatroom, String aListener, String aNickname) throws RemoteException, MalformedURLException, NotBoundException {
		this.chatroom = aChatroom;
		String url = "rmi://"+Client.server_name+"/" + aListener;
		this.listener = (Listener)Naming.lookup(url);
		this.nickname = aNickname;
	}

	public void disconnect() throws RemoteException {
		this.chatroom.disconnect(this.nickname);
	}

	public void sendMessage(String aMsg) throws RemoteException {
		this.chatroom.sendMessage(aMsg, this.nickname);
	}
	
	public void receiveMessage(String aMsg) throws IOException, RemoteException {
		this.listener.receiveMessage(aMsg);
	}

	public List<String> getAllUsers() throws RemoteException {
		return this.chatroom.getAllUsers();
	}
}