package server;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.ListenerImpl;
import exception.MaxConnectionException;

public class ChatRoom {
	
	private int max_connection = 10;
	
	/**
	 * Hashmap contenant pour chaque session les pseudos associés
	 */
	private Map<String,Session> clients;
	
	private String name;

	public ChatRoom(String aName) {
		throw new UnsupportedOperationException();
	}

	public ChatRoom(String aName, int aMax_connection) {
		this.max_connection=aMax_connection;
		this.name = name;
		this.clients = new HashMap<>();
	}

	public void disconnect(String aNickname) {
		throw new UnsupportedOperationException();
	}

	public void sendMessage(String aMsg, String aNickname) {
		throw new UnsupportedOperationException();
	}

	public Session connect(ListenerImpl aListener, String aNickname) throws MaxConnectionException {
		if (clients.size()>=max_connection) throw new MaxConnectionException();
		return null;
	}

	public boolean verifyNickname(String aNickname) {
		return !clients.containsKey(aNickname);
	}
	
	public List<String> getAllUsers(){
		List<String> res = new ArrayList<>();
		Iterator<String> it = this.clients.keySet().iterator();
		while (it.hasNext()) {
			res.add(it.next());
		}
		return res;
	}
}