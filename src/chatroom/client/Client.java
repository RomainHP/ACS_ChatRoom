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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    private HashMap<Session, Listener> link;
    
    private HashMap<Session, String> nickname_List;

    private Session session;

    private final Login login;

    private String nickname;

    private static String server_name;

    public static String name_rebind = "listener_";

    public Client(Login log) throws RemoteException {
        this.login = log;
        this.link = new HashMap();
        this.nickname_List = new HashMap();
    }

    /**
     * Link to a session, the nickname of the user for this session
     * @param ses The session that'll be linked
     * @param nick The nickname to link
     */
    public void setNickname(Session ses, String nick) {
        this.nickname_List.put(ses, nick);
        
    }
    
    /**
     * Display to the client the chatroom of a given session
     * @param ses The session of the chatroom that must be displayed/updated
     * @param out The visual for the client
     * @throws RemoteException 
     */
    public void setOutput(Session ses, Display out) throws RemoteException {
        if(this.link.containsKey(ses)){
            this.link.get(ses).setOutput(out);
        }
    }

    /**
     * Return the nickname of the client in a given session
     * @param ses The session (chatroom) you want to retrieve the nickname
     * @return The nickname of the client
     */
    public String getNickname(Session ses) {
        String nick = null;
        if(this.nickname_List.containsKey(ses)){
            nick = this.nickname_List.get(ses);
        }
        return nick;
    }

    /**
     * Set the listener of a chatroom
     * @param name The name that'll be given to the listener
     * @return A listener on the session
     */
    public Listener listen(String name) {
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
     * @return The session when connected
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
     * @param pseudo      client nickname
     * @param chat        chatroom he wants to connect to
     * @param nb_MaxUsers number max of users
     * @return The session when connected
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException
     */
    public Session connect(String pseudo, String chat, int nb_MaxUsers) throws MaxConnectionException,
            WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        String name = Client.name_rebind + chat + "_" + pseudo;
        Listener sessionListener = this.listen(name);
        String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat, nb_MaxUsers);
        this.session = (Session) Naming.lookup(url);
        this.link.put(this.session, sessionListener);
        return this.session;
    }

    /**
     * Connect the client to a chatroom
     *
     * @param pseudo   client nickname
     * @param chat     chatroom he wants to connect to
     * @param password chatroom password
     * @return The session when connected
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

     /**
     * Connect the client to a chatroom
     *
     * @param pseudo      client nickname
     * @param chat        chatroom he wants to connect to
     * @param password The password to connect to a private chatroom
     * @param nb_MaxUsers number max of users
     * @return The session when connected
     * @throws MaxConnectionException
     * @throws WrongPasswordException
     * @throws NicknameNotAvailableException
     * @throws NotBoundException
     * @throws IOException
     */
    public Session connect(String pseudo, String chat, String password, int nb_MaxUsers) throws MaxConnectionException,
            WrongPasswordException, NicknameNotAvailableException, NotBoundException, IOException {
        String name = Client.name_rebind + chat + "_" + pseudo;
        Listener sessionListener = this.listen(name);
        String url = "rmi://" + server_name + "/" + this.login.connect(pseudo, name, chat, password, nb_MaxUsers);
        this.session = (Session) Naming.lookup(url);
        this.link.put(this.session, sessionListener);
        return this.session;
    }

    /**
     * Disconnect the user from a session
     * @param aSession The session you need to disconnect from
     * @throws IOException 
     */
    public void disconnect(Session aSession) throws IOException {
        if (this.link.containsKey(aSession)) {
            for (Session ses : this.link.keySet()) {
                if(ses.equals(aSession)){
                    ses.disconnect();
                }
            }
            //unexport the listener of the client
            UnicastRemoteObject.unexportObject(this.link.get(aSession), true);
            this.link.remove(aSession);
        }
    }

    /**
     * Sens a text message in a chatroom
     * @param aSession The session you want to send a message in
     * @param aMsg The message to send
     * @throws RemoteException
     * @throws IOException 
     */
    public void sendMessage(Session aSession, String aMsg) throws RemoteException, IOException {
        if (this.link.containsKey(aSession)) {
            for(Session ses : this.link.keySet()){
                if(ses.equals(aSession)){
                    Message msg = new Message(aMsg, this.nickname_List.get(ses));
                    ses.sendMessage(msg);
                }
            }
        }
    }

    /**
     * Sens an image message in a chatroom
     * @param aSession The session you want to send a message in
     * @param aMsg The image to send
     * @throws RemoteException
     * @throws IOException 
     */
    public void sendImageMessage(Session aSession, File aMsg) throws RemoteException, IOException {
        if (this.link.containsKey(aSession)) {
            for(Session ses : this.link.keySet()){
                if(ses.equals(aSession)){
                    Message msg = new ImageMessage(aMsg, this.nickname_List.get(ses));
                    ses.sendMessage(msg);
                }
            }
        }
    }    

    /**
     * Sens a sound message in a chatroom
     * @param aSession The session you want to send a message in
     * @param aMsg The sound to send
     * @throws RemoteException
     * @throws IOException 
     */
    public void sendSoundMessage(Session aSession, File aMsg) throws RemoteException, UnsupportedAudioFileException, IOException {
        if (this.link.containsKey(aSession)) {
            for(Session ses : this.link.keySet()){
                if(ses.equals(aSession)){
                    Message msg = new SoundMessage(aMsg, this.nickname_List.get(ses));
                    ses.sendMessage(msg);
                }
            }
        } 
    }

    /**
     * Return the current session
     * @return the current session
     */
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
     * @param aSession the session in which you need to look to find the user
     * @param aMsg   message to send
     * @param nickTo nickname of the user who receive the message
     * @throws IOException
     * @throws chatroom.exception.NotFoundUserException
     */
    public void sendImageMessage(Session aSession, File aMsg, String nickTo) throws IOException, NotFoundUserException {
        if (this.link.containsKey(aSession)) {
            for(Session ses : this.link.keySet()){
                if(ses.equals(aSession)){
                    Message msg = new ImageMessage(aMsg, this.nickname_List.get(ses));
                    ses.sendMessage(msg, nickTo);
                }
            }
        }
    }

    /**
     * Send a sound message to one person present in the chatroom
     *
     * @param aSession the session in which you need to look to find the user
     * @param aMsg   message to send
     * @param nickTo nickname of the user who receive the message
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws chatroom.exception.NotFoundUserException
     */
    public void sendSoundMessage(Session aSession, File aMsg, String nickTo) throws IOException, UnsupportedAudioFileException, NotFoundUserException {
        if (this.link.containsKey(aSession)) {
            for(Session ses : this.link.keySet()){
                if(ses.equals(aSession)){
                    Message msg = new SoundMessage(aMsg, this.nickname_List.get(ses));
                    ses.sendMessage(msg, nickTo);
                }
            }
        }
    }

    /**
     * Send a message to one person present in the chatroom
     * @param aSession the session in which you need to look to find the user
     * @param aMsg   message to send
     * @param nickTo nickname of the user who receive the message
     * @throws IOException
     * @throws chatroom.exception.NotFoundUserException
     */
    public void sendMessage(Session aSession, String aMsg, String nickTo) throws IOException, NotFoundUserException {
        if (this.link.containsKey(aSession)) {
            for(Session ses : this.link.keySet()){
                if(ses.equals(aSession)){
                    Message msg = new Message(aMsg, this.nickname_List.get(ses));
                    ses.sendMessage(msg, nickTo);
                }
            }
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
