package chatroom.client.message;

import java.io.Serializable;

public class Message implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3659547225566522546L;

    private String message;

    private String nick;

    protected boolean isPrivate=false;

    public Message(String msg, String nick) {
        this.nick = nick;
        this.message = msg;
    }

    public Message(String aMsg, String nickname, boolean priv) {
        this(aMsg,nickname);
        this.isPrivate = priv;
    }

    public String getNick() {
        return this.nick;
    }

    @Override
    public String toString() {
        return this.message;
    }

    public boolean getIsPrivate(){
        return this.isPrivate;
    }

}
