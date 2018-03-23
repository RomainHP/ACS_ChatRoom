package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import client.Listener;

public class SessionImpl extends UnicastRemoteObject implements Session {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9184666239790872778L;
	private String nickname;
	private ChatRoom chatroom;
	private Listener listener;

	public SessionImpl(ChatRoom aChatroom, Listener aListener, String aNickname) throws RemoteException {
		this.chatroom = aChatroom;
		this.listener = aListener;
		this.nickname = aNickname;
	}

	public void disconnect() {
		this.chatroom.disconnect(this.nickname);
	}

	public void sendMessage(String aMsg) {
		this.chatroom.sendMessage(aMsg, this.nickname);
	}
	
	public void receiveMessage(String aMsg) throws IOException {
		this.listener.receiveMessage(aMsg);
	}

	public List<String> getAllUsers() {
		return this.chatroom.getAllUsers();
	}
}