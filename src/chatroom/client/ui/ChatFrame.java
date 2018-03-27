package chatroom.client.ui;

import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.swing.*;

import chatroom.server.Session;

public class ChatFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -301099811934446277L;

	JTextArea textToSend;

	JTextArea textReceived;

	/**
	 * list of users
	 */
	JList<JLabel> users;

	JButton validate;

	public ChatFrame(String title, Session session) throws RemoteException {
		super("Chatroom : " + title);
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblChatroom = new JLabel("ChatRoom");
		lblChatroom.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lblChatroom, BorderLayout.NORTH);

		JTextPane chatTextPane = new JTextPane();
		chatTextPane.setEditable(false);
		this.getContentPane().add(chatTextPane, BorderLayout.CENTER);

		Box horizontalBox = Box.createHorizontalBox();
		this.getContentPane().add(horizontalBox, BorderLayout.SOUTH);

		JTextField msgTextField = new JTextField();
		horizontalBox.add(msgTextField);
		msgTextField.setColumns(10);

		JButton btnSend = new JButton("Send");
		horizontalBox.add(btnSend);

		Box verticalBox = Box.createVerticalBox();
		this.getContentPane().add(verticalBox, BorderLayout.EAST);

		JLabel lblUsers = new JLabel("Users :");
		verticalBox.add(lblUsers);

		JList<String> usersList = new JList<>(session.getAllUsers());

		JScrollPane scrollPane = new JScrollPane(usersList);
		verticalBox.add(scrollPane);
	}

}
