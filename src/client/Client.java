package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

import exception.MaxConnectionException;
import exception.NicknameNotAvailableException;
import exception.WrongPasswordException;
import server.Login;
import server.Session;

public class Client {

	private Session session;

	private Listener listener;

	public static final String name_rebind = "listener";

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.err.println("manque l'argument du hostname du server");
			return;
		}
		try {
			Client client = new Client();
			// Login
			Login log = (Login) Naming.lookup(args[0]);
			client.connect(log);
			// Listen
			client.listen();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Client() throws RemoteException {
		//interface console
		this.listener = new ListenerImpl(System.out);
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

	public void connect(Login log) throws RemoteException, MaxConnectionException, WrongPasswordException, NicknameNotAvailableException, MalformedURLException, NotBoundException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Chat ?");
		String chat = sc.nextLine();
		System.out.println("Pseudo ?");
		String pseudo = sc.nextLine();
		this.session = (Session) Naming.lookup(log.connect(pseudo, listener, chat));
		sc.close();
	}

	public void disconnect() {
		if (this.session!=null) this.session.disconnect();
	}

	public void sendMessage(String aMsg) {
		if (this.session!=null) this.session.sendMessage(aMsg);
	}
}