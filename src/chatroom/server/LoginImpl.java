package chatroom.server;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginImpl extends UnicastRemoteObject implements Login {

    /**
     *
     */
    private static final long serialVersionUID = -6692808680665826670L;

    /**
     * Hashmap including for each chatroom its name
     */
    private final Map<String, ChatRoom> chatrooms;

    public LoginImpl() throws RemoteException {
        this.chatrooms = new HashMap<>();
    }

    @Override
    public String connect(String aNickname, String aListener, String aChatroom)
            throws MaxConnectionException, WrongPasswordException, NicknameNotAvailableException,
            NotBoundException, IOException {
        if (!this.chatrooms.containsKey(aChatroom)) {
            this.chatrooms.put(aChatroom, new ChatRoom(aChatroom, this));
        }
        ChatRoom chat = this.chatrooms.get(aChatroom);
        return chat.connect(aListener, aNickname);
    }

    @Override
    public String connect(String aNickname, String aListener, String aChatroom, int nb_MaxUsers)
            throws IOException, MaxConnectionException, NicknameNotAvailableException, NotBoundException, WrongPasswordException {
        if (!this.chatrooms.containsKey(aChatroom)) {
            this.chatrooms.put(aChatroom, new ChatRoom(aChatroom, this, nb_MaxUsers));
        }
        ChatRoom chat = this.chatrooms.get(aChatroom);
        return chat.connect(aListener, aNickname);
    }

    @Override
    public String connect(String aNickname, String aListener, String aChatroom, String aPassword)
            throws MaxConnectionException, WrongPasswordException, NicknameNotAvailableException,
            NotBoundException, IOException {
        if (!this.chatrooms.containsKey(aChatroom)) {
            this.chatrooms.put(aChatroom, new PrivateChatRoom(aChatroom, this, aPassword));
        }
        PrivateChatRoom chat = (PrivateChatRoom) this.chatrooms.get(aChatroom);
        return chat.connect(aListener, aNickname, aPassword);
    }

    @Override
    public String connect(String aNickname, String aListener, String aChatroom, String aPassword, int nb_MaxUsers)
            throws IOException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, NotBoundException {
        if (!this.chatrooms.containsKey(aChatroom)) {
            this.chatrooms.put(aChatroom, new PrivateChatRoom(aChatroom, this, nb_MaxUsers, aPassword));
        }
        PrivateChatRoom chat = (PrivateChatRoom) this.chatrooms.get(aChatroom);
        return chat.connect(aListener, aNickname, aPassword);
    }

    @Override
    public String[] getAllChatRoom() throws RemoteException {
        String[] res = new String[this.chatrooms.size()];
        Iterator<String> it = this.chatrooms.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            res[i] = it.next();
            i++;
        }
        return res;
    }

    @Override
    synchronized public void removeChatroom(ChatRoom c) throws RemoteException {
        this.chatrooms.remove(c.getName());
    }

    @Override
    public boolean isPrivateChatroom(String chat) throws RemoteException {
        return (this.chatrooms.get(chat) instanceof PrivateChatRoom);
    }
}
