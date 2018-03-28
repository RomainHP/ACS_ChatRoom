package chatroom.client;

import chatroom.client.ui.ExceptionPopup;
import chatroom.client.ui.LoginFrame;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.NotEnoughArgumentsException;
import chatroom.exception.WrongPasswordException;
import chatroom.server.Login;
import chatroom.server.Session;
import java.awt.EventQueue;
import java.io.IOException;

public class Client {

	private Session session;

	private Listener listener;

	private Login login;

	private String nickname;

	public static String server_name;

	public static String name_rebind = "listener_";

	public Client(Login log) throws RemoteException {
		this.login = log;
		this.listener = new ListenerImpl(System.out);
	}

	public void setNickname(String nick){
		this.nickname = nick;
	}

	public String getNickname(){
		return this.nickname;
	}

	public void setOutput(OutputStream out) throws RemoteException{
		this.listener.setOutput(out);
	}

	public void listen(String name) {
		/*Thread t = new Thread() {
			public void run() {
				System.out.println("Enregistrement de l'objet.");
				try {
					Naming.rebind(name, (Remote) Client.this.listener);
				} catch (RemoteException | MalformedURLException e) {
					e.printStackTrace();
					return;
				}
				System.out.println("listener operationnel.");
			}
		};
		t.start();*/
		System.out.println("Enregistrement de l'objet.");
		try {
			Naming.rebind(name, (Remote) Client.this.listener);
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void connect(String pseudo, String chat) throws RemoteException, MaxConnectionException,
	WrongPasswordException, NicknameNotAvailableException, MalformedURLException, NotBoundException {
		this.connect(pseudo, chat, "");
	}

	public void connect(String pseudo, String chat, String password) throws RemoteException, MaxConnectionException,
	WrongPasswordException, NicknameNotAvailableException, MalformedURLException, NotBoundException {
		String name = Client.name_rebind + chat + "_" + pseudo;
		this.listen(name);
		String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat, password);
		this.session = (Session) Naming.lookup(url);
	}

	public void disconnect() throws RemoteException {
		if (this.session != null)
			this.session.disconnect();
	}

	public void sendMessage(String aMsg) throws RemoteException, IOException {
		if (this.session != null){
			Message msg = new Message(aMsg, this.nickname);
			this.session.sendMessage(msg);
		}
	}

	public Session getSession(){
		return this.session;
	}
	
	/**
	 * Nickname only composed with letters and numbers (same for password)
	 * @param nick
	 * @return
	 */
	public static boolean verifyName(String name) {
		Pattern p = Pattern.compile("[^a-zA-Z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        return m.find();
	}

	public static void main(String[] args) {
		try {
			if (args.length <= 0) {
				throw new NotEnoughArgumentsException();
			}
			// Login
			Client.server_name = args[0];
			String url = "rmi://" + Client.server_name + "/login";
			Login log = (Login) Naming.lookup(url);
			Client client = new Client(log);
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						LoginFrame logframe = new LoginFrame(log.getAllChatRoom(), client);
						logframe.setVisible(true);
					} catch (Exception e) {
						ExceptionPopup.showError(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionPopup.showError(e);
		}
	}
}