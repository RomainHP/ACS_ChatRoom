package chatroom.client.message;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;

public class Message implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3659547225566522546L;

    private String message;

    private String nick;

    public Message(String msg, String nick) {
        this.nick = nick;
        this.message = msg;
    }

    public String getNick() {
        return this.nick;
    }

    @Override
    public String toString() {
        return this.message;
    }

}
