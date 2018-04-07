package chatroom.client;

import chatroom.client.message.ImageMessage;
import chatroom.client.message.Message;
import chatroom.client.message.SoundMessage;
import chatroom.client.ui.ExceptionPopup;
import chatroom.client.ui.MainFrame;
import chatroom.client.ui.display.ConsoleDisplay;
import chatroom.client.ui.display.Display;
import chatroom.exception.*;
import chatroom.server.Login;
import chatroom.server.Session;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    private Session session;

    private final Listener listener;

    private final Login login;

    private String nickname;

    private static String server_name;

    public static String name_rebind = "listener_";

    public Client(Login log) throws RemoteException {
        this.login = log;
        this.listener = new ListenerImpl(new ConsoleDisplay());
    }

    public void setNickname(String nick) {
        this.nickname = nick;
    }

    public void setOutput(Display out) throws RemoteException {
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
            Naming.rebind(name, Client.this.listener);
            System.out.println("listener operationnel.");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connect the client to a chatroom
     *
     * @param pseudo client nickname
     * @param chat   chatroom he wants to connect to
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException
     */
    public void connect(String pseudo, String chat) throws MaxConnectionException,
            WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        String name = Client.name_rebind + chat + "_" + pseudo;
        this.listen(name);
        String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat);
        this.session = (Session) Naming.lookup(url);
    }

    /**
     * Connect the client to a chatroom
     *
     * @param pseudo      client nickname
     * @param chat        chatroom he wants to connect to
     * @param nb_MaxUsers number max of users
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException
     */
    public void connect(String pseudo, String chat, int nb_MaxUsers) throws MaxConnectionException,
            WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        String name = Client.name_rebind + chat + "_" + pseudo;
        this.listen(name);
        String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat, nb_MaxUsers);
        this.session = (Session) Naming.lookup(url);
    }

    /**
     * Connect the client to a chatroom
     *
     * @param pseudo   client nickname
     * @param chat     chatroom he wants to connect to
     * @param password chatroom password
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException
     */
    public void connect(String pseudo, String chat, String password) throws MaxConnectionException,
            WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        String name = Client.name_rebind + chat + "_" + pseudo;
        this.listen(name);
        String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat, password);
        this.session = (Session) Naming.lookup(url);
    }

    /**
     * Connect the client to a chatroom
     *
     * @param pseudo      client nickname
     * @param chat        chatroom he wants to connect to
     * @param nb_MaxUsers number max of users
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException
     */
    public void connect(String pseudo, String chat, String password, int nb_MaxUsers) throws MaxConnectionException,
            WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        String name = Client.name_rebind + chat + "_" + pseudo;
        this.listen(name);
        String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat, password, nb_MaxUsers);
        this.session = (Session) Naming.lookup(url);
    }

    public void disconnect() throws IOException {
        if (this.session != null) {
            this.session.disconnect();
            //unexport the listener of the client
            UnicastRemoteObject.unexportObject(this.listener, true);
        }
    }

    public void sendMessage(String aMsg) throws IOException {
        if (this.session != null) {
            Message msg = new Message(aMsg, this.nickname);
            this.session.sendMessage(msg);
        }
    }

    public void sendImageMessage(File aMsg) throws RemoteException, IOException {
        if (this.session != null) {
            Message msg = new ImageMessage(aMsg, this.nickname);
            this.session.sendMessage(msg);
        }
    }

    public void sendSoundMessage(File aMsg) throws RemoteException, UnsupportedAudioFileException, IOException {
        if (this.session != null) {
            System.out.println("Client : " + aMsg);
            Message msg = new SoundMessage(aMsg, this.nickname);
            System.out.println("Client : " + ((SoundMessage) msg).getSound());
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

    /**
     * Send an image message to one person present in the chatroom
     *
     * @param aMsg   message to send
     * @param nickTo nickname of the user who receive the message
     * @throws IOException
     */
    public void sendImageMessage(File aMsg, String nickTo) throws IOException, NotFoundUserException {
        if (this.session != null) {
            Message msg = new ImageMessage(aMsg, this.nickname, true);
            this.session.sendMessage(msg, nickTo);
        }
    }

    /**
     * Send a sound message to one person present in the chatroom
     *
     * @param aMsg   message to send
     * @param nickTo nickname of the user who receive the message
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public void sendSoundMessage(File aMsg, String nickTo) throws IOException, UnsupportedAudioFileException, NotFoundUserException {
        if (this.session != null) {
            Message msg = new SoundMessage(aMsg, this.nickname, true);
            this.session.sendMessage(msg, nickTo);
        }
    }

    /**
     * Send a message to one person present in the chatroom
     *
     * @param aMsg   message to send
     * @param nickTo nickname of the user who receive the message
     * @throws IOException
     */
    public void sendMessage(String aMsg, String nickTo) throws IOException, NotFoundUserException {
        if (this.session != null) {
            Message msg = new Message(aMsg, this.nickname, true);
            this.session.sendMessage(msg, nickTo);
        }
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
