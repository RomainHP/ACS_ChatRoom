package chatroom.client.message;

/**
 * @author rcharpen
 */
public class SystemMessage extends Message {

    public SystemMessage(String msg) {
        super(msg, "@SYSTEM");
    }

}
