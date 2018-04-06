package chatroom.client.ui.display;

import chatroom.client.message.Message;

public interface Display {

    /**
     * Write a message on the display
     * @param aMsg written message
     */
    public abstract void write(Message aMsg);
}
