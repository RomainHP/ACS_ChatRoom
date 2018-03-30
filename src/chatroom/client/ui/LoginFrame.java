package chatroom.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import chatroom.client.Client;
import chatroom.exception.MaxConnectionException;
import chatroom.exception.NicknameNotAvailableException;
import chatroom.exception.UncorrectNameException;
import chatroom.exception.WrongPasswordException;
import chatroom.server.Login;

public class LoginFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -301099811934446277L;
	
	private JList<String> chatroomsList;

	public LoginFrame(Login log, Client client) throws RemoteException {
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

		JLabel lblChatroom = new JLabel("Select a ChatRoom :");
		chatroomScrollPane.add(lblChatroom);

		this.chatroomsList = new JList<>(log.getAllChatRoom());

		JScrollPane scrollPane = new JScrollPane(chatroomsList);
		chatroomScrollPane.add(scrollPane);
		
		JButton actualizeButton = new JButton("Actualize");
		scrollPane.setColumnHeaderView(actualizeButton);

		Box verticalBox_2 = Box.createVerticalBox();
		tabPane.addTab("Create", null, verticalBox_2, null);

		JLabel chatroomLabel = new JLabel("ChatRoom :");
		verticalBox_2.add(chatroomLabel);

		JTextField chatroomTextField = new JTextField();
		verticalBox_2.add(chatroomTextField);
		chatroomTextField.setColumns(10);

		JLabel lblPassword = new JLabel("Password :");
		verticalBox_2.add(lblPassword);

		JPasswordField passwordTextField = new JPasswordField();
		passwordTextField.setColumns(10);
		verticalBox_2.add(passwordTextField);

		// actualize the list of chatroom
		actualizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoginFrame.this.chatroomsList.setListData(log.getAllChatRoom());
				} catch (RemoteException e) {
					ExceptionPopup.showError(e);
				}
			}
		});
		
		// launch the chat
		btnConfirm.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					// nickname
					String pseudo = pseudoTextField.getText();
					if (!Client.verifyName(pseudo))
						throw new UncorrectNameException("nickname");
					// chat
					String chat = "";
					// password
					String password = "";
					if (tabPane.getSelectedIndex() == 0) {
						chat = chatroomsList.getSelectedValue();
						if (log.isPrivateChatroom(chat)) password = PasswordPopup.askPassword();
					} else {
						chat = chatroomTextField.getText();
						password = passwordTextField.getText();
						if (!Client.verifyName(chat))
							throw new UncorrectNameException("chat");
						if (!Client.verifyName(password))
							throw new UncorrectNameException("password");
					}
					// no password
					if (password.trim().length()==0) {
						client.connect(pseudo, chat);
					// password
					} else {
						client.connect(pseudo, chat, password);
					}
					ChatFrame chatframe = new ChatFrame(chat, client);
					client.setOutput(chatframe.getOutput());
					client.setNickname(pseudo);
					LoginFrame.this.setVisible(false);
					chatframe.setVisible(true);
				} catch (MaxConnectionException | WrongPasswordException
						| NicknameNotAvailableException | NotBoundException | UncorrectNameException | IOException e) {
					ExceptionPopup.showError(e);
				}
			}
		});

		//disconnect the client on close
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.disconnect();
				} catch (IOException e1) {
					ExceptionPopup.showError(e1);
				}

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
	}
}
