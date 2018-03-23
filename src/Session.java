public abstract class Session {
	public ChatRoom _unnamed_ChatRoom_;
	public Client _unnamed_Client_;

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