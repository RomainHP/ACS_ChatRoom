package server;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import client.Listener;
import exception.MaxConnectionException;
import exception.NicknameNotAvailableException;
import exception.WrongPasswordException;

public interface Login extends Remote{

	abstract String connect(String aNickname, Listener aListener, String aChatroom) throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, MalformedURLException;

	abstract String connect(String aNickname, Listener aListener, String aChatroom, String aPassword) throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, MalformedURLException;
	
	abstract List<String> getAllChatRoom();
}