public class SessionImpl implements Session {
	private String _nickname;
	private ChatRoom _chatroom;
	private ListenerImpl _listener;
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

	public String getAllUsers() {
		throw new UnsupportedOperationException();
	}
}