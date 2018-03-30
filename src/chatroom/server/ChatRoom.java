package chatroom.server;

import chatroom.client.Message;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;
import java.io.IOException;

public class ChatRoom {

	private int max_connection = 10;
	
	private Login log;
	
	private String name;

	/**
	 * Hashmap contenant pour chaque session les pseudos associes
	 */
	private Map<String, Session> clients;

	public ChatRoom(String name, Login log) {
		this.name = name;
		this.log = log;
		this.clients = new HashMap<>();
	}

	public ChatRoom(String name, Login log, int aMax_connection) {
		this(name, log);
		this.max_connection = aMax_connection;
	}
	
	public String getName() {
		return this.name;
	}

	public void disconnect(String aNickname) throws RemoteException, IOException {
		//unexport the session of the client
		UnicastRemoteObject.unexportObject(this.clients.get(aNickname), true);
		//Bye message
		this.sendMessage(new Message("Bye " + aNickname + " !"));
		this.clients.remove(aNickname);
		if (this.clients.isEmpty()) {
			this.log.removeChatroom(this);
		}
	}

	public void sendMessage(Message aMsg) throws RemoteException, IOException {
		// Broadcast to all clients
		Iterator<Session> it = clients.values().iterator();
		while (it.hasNext()) {
			it.next().receiveMessage(aMsg);
		}
	}

	synchronized public String connect(String aListener, String aNickname)
			throws MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
		if (clients.size() >= max_connection) {
			throw new MaxConnectionException();
		}
		if (this.verifyNickname(aNickname)) {
			Session session = new SessionImpl(this, aListener, aNickname);
			// rebind client session
			String name = "client_" + aNickname;
			Naming.rebind(name, session);
			this.clients.put(aNickname, session);
			//Welcome message
			this.sendMessage(new Message("Please welcome to " + aNickname));
			return name;
		} else {
			throw new NicknameNotAvailableException();
		}
	}

	synchronized public String connect(String aListener, String aNickname, String aPassword)
			throws MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
		return this.connect(aListener, aNickname);
	}

	public boolean verifyNickname(String aNickname) {
		return !clients.containsKey(aNickname);
	}

	public List<String> getAllUsers() {
		List<String> res = new ArrayList<>();
		Iterator<String> it = this.clients.keySet().iterator();
		while (it.hasNext()) {
			res.add(it.next());
		}
		return res;
	}
}
