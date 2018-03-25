package chatroom.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import chatroom.client.Client;
import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;
import chatroom.server.Login;

public class LoginFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -301099811934446277L;

	/*
	 * JTextArea nickname;
	 * 
	 * JTextArea chatroom;
	 * 
	 * JList<String> chatroomList;
	 * 
	 * JScrollPane chatroomListScoller;
	 * 
	 * JButton confirm;
	 * 
	 * public LoginFrame(String[] chats) { super("Login");
	 * 
	 * this.nickname = new JTextArea(10, 10); this.chatroom = new JTextArea(10, 10);
	 * this.chatroomList = new JList<>(chats); this.chatroomListScoller = new
	 * JScrollPane(this.chatroomList); this.chatroomListScoller.setPreferredSize(new
	 * Dimension(250, 80)); this.confirm = new JButton("Confirm");
	 * 
	 * //Add this.add(this.nickname, BorderLayout.WEST);
	 * this.add(this.chatroomListScoller, BorderLayout.EAST); this.add(this.confirm,
	 * BorderLayout.SOUTH);
	 * 
	 * this.setPreferredSize(new Dimension(400,300)); this.pack(); }
	 */
	
	private ChatFrame chat;

	public LoginFrame(String[] chats, Client client) {
		super("Login");
		this.setBounds(100, 100, 444, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Box verticalBox = Box.createVerticalBox();
		this.getContentPane().add(verticalBox, BorderLayout.WEST);

		JLabel lblPseudo = new JLabel("Nickname :");
		verticalBox.add(lblPseudo);

		JTextField textField = new JTextField();
		verticalBox.add(textField);
		textField.setColumns(10);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lblLogin, BorderLayout.NORTH);

		JButton btnConfirm = new JButton("Confirm");
		this.getContentPane().add(btnConfirm, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		Box verticalBox_1 = Box.createVerticalBox();
		tabbedPane.addTab("Join", null, verticalBox_1, null);

		JLabel lblChatroom = new JLabel("ChatRoom :");
		verticalBox_1.add(lblChatroom);

		JList<String> list_1 = new JList<>(chats);

		JScrollPane scrollPane = new JScrollPane(list_1);
		verticalBox_1.add(scrollPane);

		Box verticalBox_2 = Box.createVerticalBox();
		tabbedPane.addTab("Create", null, verticalBox_2, null);

		JLabel label = new JLabel("ChatRoom :");
		verticalBox_2.add(label);

		JTextField textField_1 = new JTextField();
		verticalBox_2.add(textField_1);
		textField_1.setColumns(10);

		//launch the chat
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pseudo = textField.getText();
				String chat = "";
				if (tabbedPane.areFocusTraversalKeysSet(0)) {
					chat = list_1.getSelectedValue();
				} else {
					chat = textField_1.getText();
				}
				try {
					client.connect(pseudo, chat);
				} catch (RemoteException | MalformedURLException | MaxConnectionException | WrongPasswordException
						| NicknameNotAvailableException | NotBoundException e) {
					ExceptionPopup.showError(e);
				}
			}
		});
	}

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.err.println("Il manque l'argument du hostname du server");
			return;
		}
		try {
			// Login
			Client.server_name = args[0];
			String url = "rmi://" + Client.server_name + "/login";
			Login log = (Login) Naming.lookup(url);
			Client client = new Client(log,System.out);
			EventQueue.invokeLater(new Runnable() {
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
