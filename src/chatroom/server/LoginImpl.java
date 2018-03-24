package chatroom.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

public class LoginImpl extends UnicastRemoteObject implements Login {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6692808680665826670L;

	/**
	 * Hashmap contenant pour chaque chatroom le nom correspondant
	 */
	private Map<String, ChatRoom> chatrooms;

	public LoginImpl() throws RemoteException {
		this.chatrooms = new HashMap<>();
	}

	@Override
	public String connect(String aNickname, String aListener, String aChatroom) 
			throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, MalformedURLException, NotBoundException 
	{
		if (!this.chatrooms.containsKey(aChatroom)) this.chatrooms.put(aChatroom, new ChatRoom());
		ChatRoom chat = this.chatrooms.get(aChatroom);
		return chat.connect(aListener, aNickname);
	}

	@Override
	public String connect(String aNickname, String aListener, String aChatroom, String aPassword) 
			throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, MalformedURLException, NotBoundException 
	{
		if (!this.chatrooms.containsKey(aChatroom)) this.chatrooms.put(aChatroom, new ChatRoom());
		ChatRoom chat = this.chatrooms.get(aChatroom);
		return chat.connect(aListener, aNickname, aPassword);
	}

	@Override
	public List<String> getAllChatRoom() throws RemoteException {
		List<String> res = new ArrayList<>();
		Iterator<String> it = this.chatrooms.keySet().iterator();
		while (it.hasNext()) {
			res.add(it.next());
		}
		return res;
	}
}