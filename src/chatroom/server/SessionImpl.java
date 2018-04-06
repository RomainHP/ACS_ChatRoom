package chatroom.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import chatroom.client.Listener;
import chatroom.client.message.Message;

public class SessionImpl extends UnicastRemoteObject implements Session {

    /**
     *
     */
    private static final long serialVersionUID = -9184666239790872778L;
    private final String nickname;
    private final ChatRoom chatroom;
    private final Listener listener;

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
    public void receiveMessage(Message aMsg) throws IOException, RemoteException {
        this.listener.receiveMessage(aMsg);
    }

    @Override
    public String[] getAllUsers() throws RemoteException {
        return this.chatroom.getAllUsers();
    }
}
