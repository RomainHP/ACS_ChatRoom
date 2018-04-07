package chatroom.server;

import chatroom.client.Listener;
import chatroom.client.message.Message;
import chatroom.exception.NotFoundUserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SessionImpl extends UnicastRemoteObject implements Session {

    /**
     *
     */
    private static final long serialVersionUID = -9184666239790872778L;

    /**
     * user nickname in chatroom
     */
    private final String nickname;

    /**
     * the chatroom
     */
    private final ChatRoom chatroom;

    /**
     * listener which allow the chatroom to talk to the user
     */
    private final Listener listener;

    /**
     * Constructor of SessionImpl class
     *
     * @param aChatroom chatroom linked
     * @param aListener name of rmi url of client listener
     * @param aNickname client nickname in the chatroom
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws NotBoundException
     */
    public SessionImpl(ChatRoom aChatroom, String aListener, String aNickname)
            throws RemoteException, MalformedURLException, NotBoundException {
        this.chatroom = aChatroom;
        String url = "rmi://localhost/" + aListener;
        this.listener = (Listener) Naming.lookup(url);
        this.nickname = aNickname;
    }

    @Override
    public void disconnect() throws IOException {
        this.chatroom.disconnect(this.nickname);
    }

    @Override
    public void sendMessage(Message aMsg) throws RemoteException, IOException {
        this.chatroom.sendMessage(aMsg);
    }

    @Override
    public void sendMessage(Message msg, String nickTo) throws RemoteException, IOException, NotFoundUserException {
        this.chatroom.sendMessage(msg, this.nickname, nickTo);
    }

    @Override
    public void receiveMessage(Message aMsg) throws IOException, RemoteException {
        this.listener.receiveMessage(aMsg);
    }

    @Override
    public String[] getAllUsers() throws RemoteException {
        return this.chatroom.getAllUsers();
    }
}
