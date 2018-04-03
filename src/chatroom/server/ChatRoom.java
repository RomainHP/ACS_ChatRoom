package chatroom.server;

import chatroom.client.Message;
import java.rmi.Naming;
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
import java.io.IOException;

public class ChatRoom {

    private int max_connection = 10;

    private Login log;

    private String name;

    /**
     * Hashmap containing for each session the associated client nickname
     */
    private Map<String, Session> clients;

    public ChatRoom(String name, Login log) {
        this.name = name;
        this.log = log;
        this.clients = new HashMap<>();
    }

    public ChatRoom(String name, Login log, int aMax_connection) {
        this(name, log);
        this.max_connection = aMax_connection;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Disconnect the client to the chatroom
     * if the chatroom is now empty, we remove it from Login
     * @param aNickname client disconnected
     * @throws RemoteException
     * @throws IOException 
     */
    public void disconnect(String aNickname) throws RemoteException, IOException {
        //unexport the session of the client
        UnicastRemoteObject.unexportObject(this.clients.get(aNickname), true);
        this.clients.remove(aNickname);
        //when a chatroom is empty, we remove it
        if (this.clients.isEmpty()) {
            this.log.removeChatroom(this);
        } else {
            //Bye message
            this.sendMessage(new Message("Bye " + aNickname + " !"));
        }
    }

    /**
     * Send a message to all clients in chatroom
     * @param aMsg message sent
     * @throws RemoteException
     * @throws IOException 
     */
    public void sendMessage(Message aMsg) throws RemoteException, IOException {
        // Broadcast to all clients
        Iterator<Session> it = clients.values().iterator();
        while (it.hasNext()) {
            it.next().receiveMessage(aMsg);
        }
    }

    /**
     * Connect a client to the chatroom
     * @param aListener client listener
     * @param aNickname client nickname
     * @return the name of client session (name of the rmi service)
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException 
     */
    synchronized public String connect(String aListener, String aNickname)
            throws MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        if (clients.size() >= max_connection) {
            throw new MaxConnectionException();
        }
        if (this.verifyNickname(aNickname)) {
            Session session = new SessionImpl(this, aListener, aNickname);
            // rebind client session
            String clientname = "client_" + aNickname;
            Naming.rebind(clientname, session);
            this.clients.put(aNickname, session);
            //Welcome message
            if (this.clients.size() > 1) {
                this.sendMessage(new Message("Please welcome to " + aNickname));
            }
            return clientname;
        } else {
            throw new NicknameNotAvailableException();
        }
    }

    /**
     * Connect a client to the chatroom
     * @param aListener client listener
     * @param aNickname client nickname
     * @param aPassword not used here
     * @return the name of client session (name of the rmi service)
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException 
     */
    synchronized public String connect(String aListener, String aNickname, String aPassword)
            throws MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        return this.connect(aListener, aNickname);
    }

    /**
     * Return true if the nickname is correct (not special characters, size between 1 and 15)
     * @param aNickname nickname verified
     * @return true if the nickname is correct
     */
    public boolean verifyNickname(String aNickname) {
        return !clients.containsKey(aNickname);
    }

    /**
     * return true if the chatroom is private
     * @return true if the chatroom is private
     */
    public List<String> getAllUsers() {
        List<String> res = new ArrayList<>();
        Iterator<String> it = this.clients.keySet().iterator();
        while (it.hasNext()) {
            res.add(it.next());
        }
        return res;
    }
}
