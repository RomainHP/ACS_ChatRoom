package chatroom.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

public class PrivateChatRoom extends ChatRoom {

	private String password;

	public PrivateChatRoom(String aPassword) {
		super();
		this.password = aPassword;
	}

	public PrivateChatRoom(int aMax_connection, String aPassword) {
		super(aMax_connection);
		this.password = aPassword;
	}

	@Override
	synchronized public String connect(String aListener, String aNickname) throws WrongPasswordException {
		throw new WrongPasswordException();
	}

	@Override
	synchronized public String connect(String aListener, String aNickname, String aPassword)
			throws WrongPasswordException, MaxConnectionException, RemoteException, NicknameNotAvailableException,
			MalformedURLException, NotBoundException {
		if (!aPassword.equals(password))
			throw new WrongPasswordException();
		return super.connect(aListener, aNickname);
	}
}