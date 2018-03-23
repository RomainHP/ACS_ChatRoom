package server;
import java.util.List;

import client.Listener;
import client.ListenerImpl;

public class SessionImpl implements Session {
	private String nickname;
	private ChatRoom chatroom;
	private ListenerImpl listener;
	public Listener _unnamed_Listener_;

	public SessionImpl(ChatRoom aChatroom, ListenerImpl aListener, String aNickname) {
		throw new UnsupportedOperationException();
	}

	public void disconnect() {
		throw new UnsupportedOperationException();
	}

	public void sendMessage(String aMsg) {
		throw new UnsupportedOperationException();
	}

	public List<String> getAllUsers() {
		throw new UnsupportedOperationException();
	}
}