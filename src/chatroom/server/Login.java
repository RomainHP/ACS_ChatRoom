package chatroom.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

public interface Login extends Remote {

	abstract String connect(String aNickname, String aListener, String aChatroom)
			throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException,
			MalformedURLException, NotBoundException, IOException;

	abstract String connect(String aNickname, String aListener, String aChatroom, String aPassword)
			throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException,
			MalformedURLException, NotBoundException, IOException;

	abstract String[] getAllChatRoom() throws RemoteException;
	
	abstract void removeChatroom(ChatRoom c) throws RemoteException;

	abstract boolean isPrivateChatroom(String chat) throws RemoteException;
}