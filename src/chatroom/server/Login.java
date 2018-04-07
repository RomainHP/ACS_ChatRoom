package chatroom.server;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Login extends Remote {

    /**
     * Connect the client to a chatroom
     *
     * @param aNickname client nickname
     * @param aListener client listener (name of rmi service)
     * @param aChatroom chatroom name
     * @return rmi service name for the client session
     * @throws RemoteException
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws MalformedURLException
     * @throws NotBoundException
     * @throws IOException
     */
    String connect(String aNickname, String aListener, String aChatroom)
            throws RemoteException, MaxConnectionException, NicknameNotAvailableException,
            MalformedURLException, NotBoundException, IOException, WrongPasswordException;

    /**
     * Connect the client to a chatroom
     *
     * @param aNickname   client nickname
     * @param aListener   client listener (name of rmi service)
     * @param aChatroom   chatroom name
     * @param nb_MaxUsers nb max of users in the chat room
     * @return rmi service name for the client session
     * @throws RemoteException
     * @throws MaxConnectionException
     * @throws NicknameNotAvailableException
     * @throws MalformedURLException
     * @throws NotBoundException
     * @throws IOException
     */
    String connect(String aNickname, String aListener, String aChatroom, int nb_MaxUsers)
            throws RemoteException, MaxConnectionException, NicknameNotAvailableException,
            MalformedURLException, NotBoundException, IOException, WrongPasswordException;

    /**
     * Connect the client to a chatroom
     *
     * @param aNickname client nickname
     * @param aListener client listener (name of rmi service)
     * @param aChatroom chatroom name
     * @param aPassword chatroom password
     * @return rmi service name for the client session
     * @throws RemoteException
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws MalformedURLException
     * @throws NotBoundException
     * @throws IOException
     */
    String connect(String aNickname, String aListener, String aChatroom, String aPassword)
            throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException,
            MalformedURLException, NotBoundException, IOException;

    /**
     * Connect the client to a chatroom
     *
     * @param aNickname   client nickname
     * @param aListener   client listener (name of rmi service)
     * @param aChatroom   chatroom name
     * @param aPassword   chatroom password
     * @param nb_MaxUsers nb max of users in the chat room
     * @return rmi service name for the client session
     * @throws RemoteException
     * @throws MaxConnectionException
     * @throws NicknameNotAvailableException
     * @throws MalformedURLException
     * @throws NotBoundException
     * @throws IOException
     */
    String connect(String aNickname, String aListener, String aChatroom, String aPassword, int nb_MaxUsers)
            throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException,
            MalformedURLException, NotBoundException, IOException;

    /**
     * return a tab containing the name of all chatroom
     *
     * @return a tab containing the name of all chatroom
     * @throws RemoteException
     */
    String[] getAllChatRoom() throws RemoteException;

    /**
     * remove a chatroom from the hashmap
     *
     * @param c chatroom we want to remove
     * @throws RemoteException
     */
    void removeChatroom(ChatRoom c) throws RemoteException;

    /**
     * return true if the chatroom is private
     *
     * @param chat chatroom name
     * @return true if the chatroom is private
     * @throws RemoteException
     */
    boolean isPrivateChatroom(String chat) throws RemoteException;
}
