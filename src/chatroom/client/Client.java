package chatroom.client;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

import chatroom.client.ui.LoginFrame;
import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;
import chatroom.server.Login;
import chatroom.server.Session;

public class Client {

	private Session session;

	private Listener listener;

	public static String name_rebind = "listener_";

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.err.println("Il manque l'argument du hostname du server");
			return;
		}
		try {
			String[] chats = {"chat1", "chat2"};
			Client client = new Client(chats);
			// Login
			Login log = (Login) Naming.lookup(args[0]);
			client.connect(log);
			// Listen
			client.listen();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}

	public Client(String[] chats) throws RemoteException {
		this.listener = new ListenerImpl(System.out);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame logframe = new LoginFrame(chats);
					logframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		Client.name_rebind += chat + "_" + pseudo;
		this.session = (Session) Naming.lookup(log.connect(pseudo, Client.name_rebind, chat));
		sc.close();
	}

	public void disconnect() throws RemoteException {
		if (this.session!=null) this.session.disconnect();
	}

	public void sendMessage(String aMsg) throws RemoteException {
		if (this.session!=null) this.session.sendMessage(aMsg);
	}
}