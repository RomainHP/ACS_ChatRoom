package server;
import client.ListenerImpl;
import exception.MaxConnectionException;
import exception.WrongPasswordException;

public class PrivateChatRoom extends ChatRoom {
	
	private String password;

	public PrivateChatRoom(String aName, String aPassword) {
		super(aName);
		this.password = aPassword;
	}

	public PrivateChatRoom(String aName, int aMax_connection, String aPassword) {
		super(aName,aMax_connection);
		this.password = aPassword;
	}

	public void connect(ListenerImpl aListener, String aNickname, String aPassword) throws WrongPasswordException, MaxConnectionException {
		if (!aPassword.equals(password)) throw new WrongPasswordException();
		super.connect(aListener, aNickname);
	}
}