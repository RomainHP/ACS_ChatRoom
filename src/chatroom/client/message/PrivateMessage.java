package chatroom.client.message;

/**
 * Private message between 2 users
 */
public class PrivateMessage extends Message {

    public PrivateMessage(String msg, String nick) {
        super(msg, nick);
    }
}
