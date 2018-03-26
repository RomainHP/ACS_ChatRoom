package chatroom.client;

import java.io.IOException;
import java.rmi.Remote;

public interface Listener extends Remote {

	public void receiveMessage(String aMsg) throws IOException;
}