package chatroom.client;

import chatroom.client.ui.ConsoleDisplay;
import chatroom.client.ui.Display;
import chatroom.client.ui.ExceptionPopup;
import chatroom.client.ui.MainFrame;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.NotEnoughArgumentsException;
import chatroom.exception.WrongPasswordException;
import chatroom.server.Login;
import chatroom.server.Session;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Client {

    private HashMap<Session, Listener> link;

    private Session session;

    //private final Listener listener;

    private final Login login;

    private String nickname;

    public static String server_name;

    public static String name_rebind = "listener_";

    public Client(Login log) throws RemoteException {
        this.login = log;
        this.link = new HashMap();
    }

    public void setNickname(String nick) {
        this.nickname = nick;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setOutput(Session ses, Display out) throws RemoteException {
        if(this.link.containsKey(ses)){
            this.link.get(ses).setOutput(out);
        }
    }

    public Listener listen(String name) {
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
            Listener sessionListener = new ListenerImpl(new ConsoleDisplay());
            Naming.rebind(name, (Remote) sessionListener);
            System.out.println("listener operationnel.");
            return sessionListener;
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Connect the client to a chatroom
     *
     * @param pseudo client nickname
     * @param chat chatroom he wants to connect to
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException
     */
    public Session connect(String pseudo, String chat) throws MaxConnectionException,
            WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        String name = Client.name_rebind + chat + "_" + pseudo;
        Listener sessionListener = this.listen(name);
        String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat);
        this.session = (Session) Naming.lookup(url);
        this.link.put(this.session, sessionListener);
        return this.session;
    }

    /**
     * Connect the client to a chatroom
     *
     * @param pseudo client nickname
     * @param chat chatroom he wants to connect to
     * @param password chatroom password
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException
     */
    public Session connect(String pseudo, String chat, String password) throws MaxConnectionException,
            WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        String name = Client.name_rebind + chat + "_" + pseudo;
        Listener sessionListener = this.listen(name);
        String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat, password);
        this.session = (Session) Naming.lookup(url);
        this.link.put(this.session, sessionListener);
        return this.session;
    }

    public void disconnect(Session aSession) throws IOException {
        if (this.link.containsKey(aSession)) {
            for (Session ses : this.link.keySet()) {
                if(ses.equals(aSession)){
                    ses.disconnect();
                }
            }
            this.link.remove(aSession);
            //unexport the listener of the client
            UnicastRemoteObject.unexportObject(this.link.get(aSession), true);
        }
    }

    public void sendMessage(String aMsg) throws RemoteException, IOException {
        if (this.session != null) {
            Message msg = new Message(aMsg, this.nickname);
            this.session.sendMessage(msg);
        }
    }

    public void sendMessage(File aMsg) throws RemoteException, IOException {
        if (this.session != null) {
            Message msg = new Message(aMsg, this.nickname);
            this.session.sendMessage(msg);
        }
    }

    public Session getSession() {
        return this.session;
    }

    /**
     * Nickname only composed with letters and numbers (same for password)
     *
     * @param name nickname
     * @return true if the nickname is correct
     */
    public static boolean verifyName(String name) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        return (!m.find() && name.length() > 0 && name.length() < 16);
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
            EventQueue.invokeLater(() -> {
                try {
                    MainFrame frame = new MainFrame(log, client);
                    frame.setVisible(true);
                } catch (Exception e) {
                    ExceptionPopup.showError(e);
                }
            });
        } catch (NotEnoughArgumentsException | NotBoundException | MalformedURLException | RemoteException e) {
            ExceptionPopup.showError(e);
        }
    }
}
