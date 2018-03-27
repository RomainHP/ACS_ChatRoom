 package chatroom.server;

import java.rmi.Naming;
import java.rmi.Remote;

public class Server {

	public static final String rebind_name = "login";

	public static void main(String[] args) {
		Login log = null;
		try {
			System.out.println("Creation de l'objet.");
			log = new LoginImpl();
			System.out.println("Enregistrement de l'objet.");
			Naming.rebind(rebind_name, (Remote) log);
			System.out.println("serveur operationnel.");
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}
}