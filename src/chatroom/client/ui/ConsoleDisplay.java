package chatroom.client.ui;

import chatroom.client.Message;

public class ConsoleDisplay implements Display {

    @Override
    public void write(Message aMsg) {
        System.out.println("[" + aMsg.getNick() + "] :     " + aMsg);
    }

}
