package server;
import java.util.List;

public interface Session {

	public void disconnect();

	public void sendMessage(String aMsg);

	public List<String> getAllUsers();
}