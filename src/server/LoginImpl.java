package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.Listener;
import exception.MaxConnectionException;
import exception.NicknameNotAvailableException;
import exception.WrongPasswordException;

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
	public String connect(String aNickname, Listener aListener, String aChatroom) throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, MalformedURLException {
		if (!this.chatrooms.containsKey(aChatroom)) this.chatrooms.put(aChatroom, new ChatRoom());
		ChatRoom chat = this.chatrooms.get(aChatroom);
		return chat.connect(aListener, aNickname);
	}

	@Override
	public String connect(String aNickname, Listener aListener, String aChatroom, String aPassword) throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, MalformedURLException {
		if (!this.chatrooms.containsKey(aChatroom)) this.chatrooms.put(aChatroom, new ChatRoom());
		ChatRoom chat = this.chatrooms.get(aChatroom);
		return chat.connect(aListener, aNickname, aPassword);
	}

	@Override
	public List<String> getAllChatRoom() {
		List<String> res = new ArrayList<>();
		Iterator<String> it = this.chatrooms.keySet().iterator();
		while (it.hasNext()) {
			res.add(it.next());
		}
		return res;
	}
}