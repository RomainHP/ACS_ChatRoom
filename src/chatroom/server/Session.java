package chatroom.server;

import chatroom.client.message.Message;
import chatroom.exception.NotFoundUserException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Session extends Remote {

    void disconnect() throws IOException;

    void sendMessage(Message aMsg) throws IOException;

    void sendMessage(Message msg, String nickTo) throws IOException, NotFoundUserException;

    void receiveMessage(Message aMsg) throws IOException;

    String[] getAllUsers() throws RemoteException;
}
