package server;
import java.util.HashMap;
import java.util.Map;

import client.ListenerImpl;

public class LoginImpl implements Login {
	/**
	 * Hashmap contenant pour chaque chatroom le nom correspondant
	 */
	private Map<String,ChatRoom> chatrooms;

	public LoginImpl() {
		this.chatrooms = new HashMap<>();
	}

	public Session connect(String aNickname, ListenerImpl aListener, String aChatroom) {
		throw new UnsupportedOperationException();
	}

	public Session connect(String aNickname, ListenerImpl aListener, String aChatroom, String aPassword) {
		throw new UnsupportedOperationException();
	}
}