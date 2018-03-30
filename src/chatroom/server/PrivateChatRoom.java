package chatroom.server;

import java.io.IOException;
import java.rmi.NotBoundException;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

public class PrivateChatRoom extends ChatRoom {

    private String password;

    public PrivateChatRoom(String name, Login log, String aPassword) {
        super(name, log);
        this.password = aPassword;
    }

    public PrivateChatRoom(String name, Login log, int aMax_connection, String aPassword) {
        super(name, log, aMax_connection);
        this.password = aPassword;
    }

    @Override
    synchronized public String connect(String aListener, String aNickname) throws WrongPasswordException {
        throw new WrongPasswordException();
    }

    @Override
    synchronized public String connect(String aListener, String aNickname, String aPassword)
            throws WrongPasswordException, MaxConnectionException, NicknameNotAvailableException,
            NotBoundException, IOException {
        if (!aPassword.equals(password)) {
            throw new WrongPasswordException();
        }
        return super.connect(aListener, aNickname);
    }
}
