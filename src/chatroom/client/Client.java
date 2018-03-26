package chatroom.client;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;
import chatroom.server.Login;
import chatroom.server.Session;

public class Client {

	private Session session;

	private Listener listener;

	private Login login;

	public static String server_name;

	public static String name_rebind = "listener_";

	public Client(Login log, OutputStream out) throws RemoteException {
		this.login = log;
		this.listener = new ListenerImpl(out);
	}

	public void listen() {
		Thread t = new Thread() {
			public void run() {
				System.out.println("Enregistrement de l'objet.");
				try {
					Naming.rebind(name_rebind, (Remote) Client.this.listener);
				} catch (RemoteException | MalformedURLException e) {
					e.printStackTrace();
					return;
				}
				System.out.println("listener operationnel.");
			}
		};
		t.start();
	}

	public void connect(String pseudo, String chat) throws RemoteException, MaxConnectionException,
			WrongPasswordException, NicknameNotAvailableException, MalformedURLException, NotBoundException {
		this.connect(pseudo, chat, "");
	}

	public void connect(String pseudo, String chat, String password) throws RemoteException, MaxConnectionException,
			WrongPasswordException, NicknameNotAvailableException, MalformedURLException, NotBoundException {
		String name = Client.name_rebind + chat + "_" + pseudo;
		String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat, password);
		this.session = (Session) Naming.lookup(url);
	}

	public void disconnect() throws RemoteException {
		if (this.session != null)
			this.session.disconnect();
	}

	public void sendMessage(String aMsg) throws RemoteException {
		if (this.session != null)
			this.session.sendMessage(aMsg);
	}
}