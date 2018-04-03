package chatroom.client;

import java.io.File;
import java.io.Serializable;

public class Message implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3659547225566522546L;

    TypeMessage type;

    String message;

    String nick;

    File image;

    public Message(String msg, String nick) {
        this.nick = nick;
        this.message = msg;
        this.type = TypeMessage.MESSAGE;
    }

    public Message(File image, String nick) {
        this.image = image;
        this.nick = nick;
        this.message = image.toString();
        this.type = TypeMessage.IMAGE;
    }

    public Message(String msg) {
        this.nick = "SYSTEM";
        this.message = msg;
        this.type = TypeMessage.SYSTEM;
    }

    public TypeMessage getType() {
        return this.type;
    }

    public File getImage() {
        return this.image;
    }

    public String getNick() {
        return this.nick;
    }

    @Override
    public String toString() {
        return this.message;
    }

}
