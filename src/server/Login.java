package server;
import client.ListenerImpl;

public interface Login {

	public Session connect(String aNickname, ListenerImpl aListener, String aChatroom);
}