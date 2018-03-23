package server;
import java.io.IOException;
import java.rmi.Remote;
import java.util.List;

public interface Session extends Remote {

	abstract void disconnect();

	abstract void sendMessage(String aMsg);
	
	abstract void receiveMessage(String aMsg) throws IOException;

	abstract List<String> getAllUsers();
}