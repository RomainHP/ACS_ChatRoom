package chatroom.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

public interface Login extends Remote {

	abstract String connect(String aNickname, String aListener, String aChatroom)
			throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException,
			MalformedURLException, NotBoundException;

	abstract String connect(String aNickname, String aListener, String aChatroom, String aPassword)
			throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException,
			MalformedURLException, NotBoundException;

	abstract List<String> getAllChatRoom() throws RemoteException;
}