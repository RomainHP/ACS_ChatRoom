package chatroom.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import chatroom.client.Client;
import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.WrongPasswordException;

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

	public LoginFrame(String[] chats, Client client) {
		super("Login");
		this.setBounds(100, 100, 444, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Box verticalBox = Box.createVerticalBox();
		this.getContentPane().add(verticalBox, BorderLayout.WEST);

		JLabel lblPseudo = new JLabel("Nickname :");
		verticalBox.add(lblPseudo);

		JTextField pseudoTextField = new JTextField();
		verticalBox.add(pseudoTextField);
		pseudoTextField.setColumns(10);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lblLogin, BorderLayout.NORTH);

		JButton btnConfirm = new JButton("Confirm");
		this.getContentPane().add(btnConfirm, BorderLayout.SOUTH);

		JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
		this.getContentPane().add(tabPane, BorderLayout.CENTER);

		Box chatroomScrollPane = Box.createVerticalBox();
		tabPane.addTab("Join", null, chatroomScrollPane, null);

		JLabel lblChatroom = new JLabel("ChatRoom :");
		chatroomScrollPane.add(lblChatroom);

		JList<String> chatroomsList = new JList<>(chats);

		JScrollPane scrollPane = new JScrollPane(chatroomsList);
		chatroomScrollPane.add(scrollPane);

		Box verticalBox_2 = Box.createVerticalBox();
		tabPane.addTab("Create", null, verticalBox_2, null);

		JLabel chatroomLabel = new JLabel("ChatRoom :");
		verticalBox_2.add(chatroomLabel);

		JTextField chatroomTextField = new JTextField();
		verticalBox_2.add(chatroomTextField);
		chatroomTextField.setColumns(10);

		JLabel lblPassword = new JLabel("Password :");
		verticalBox_2.add(lblPassword);

		JTextField passwordTextField = new JTextField();
		passwordTextField.setColumns(10);
		verticalBox_2.add(passwordTextField);

		// launch the chat
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pseudo = pseudoTextField.getText();
				String chat = "";
				if (tabPane.getSelectedIndex()==0) {
					chat = chatroomsList.getSelectedValue();
				} else {
					chat = chatroomTextField.getText();
				}
				String password = passwordTextField.getText();
				try {
					client.connect(pseudo, chat, password);
                                        ChatFrame chatframe = new ChatFrame(chat,client.getSession());
                                        client.setOutput(chatframe.getOuput());
                                        LoginFrame.this.setVisible(false);
                                        chatframe.setVisible(true);
				} catch (RemoteException | MalformedURLException | MaxConnectionException | WrongPasswordException
						| NicknameNotAvailableException | NotBoundException e) {
					ExceptionPopup.showError(e);
				}
			}
		});
	}
}
