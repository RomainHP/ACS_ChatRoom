package chatroom.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

public class ChatRoom {

	private int max_connection = 10;

	/**
	 * Hashmap contenant pour chaque session les pseudos associes
	 */
	private Map<String, Session> clients;

	public ChatRoom() {
		this.clients = new HashMap<>();
	}

	public ChatRoom(int aMax_connection) {
		this();
		this.max_connection = aMax_connection;
	}

	public void disconnect(String aNickname) {
		this.clients.remove(aNickname);
	}

	public void sendMessage(String aMsg, String aNickname) throws RemoteException {
		// Broadcast to all clients
		Iterator<Session> it = clients.values().iterator();
		while (it.hasNext()) {
			it.next().sendMessage(aNickname + " : " + aMsg);
		}
	}

	synchronized public String connect(String aListener, String aNickname)
			throws MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, RemoteException,
			MalformedURLException, NotBoundException {
		if (clients.size() >= max_connection)
			throw new MaxConnectionException();
		if (this.verifyNickname(aNickname)) {
			Session session = new SessionImpl(this, aListener, aNickname);
			// rebind client session
			String name = "client_" + aNickname;
			Naming.rebind(name, session);
			this.clients.put(aNickname, session);
			return name;
		} else {
			throw new NicknameNotAvailableException();
		}
	}

	synchronized public String connect(String aListener, String aNickname, String aPassword)
			throws MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, RemoteException,
			MalformedURLException, NotBoundException {
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