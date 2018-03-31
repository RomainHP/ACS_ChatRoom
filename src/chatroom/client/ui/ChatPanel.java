package chatroom.client.ui;

import chatroom.client.Client;

import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -301099811934446277L;

	MessagePanelDisplay chatTextArea;

	public ChatPanel(Client client) throws RemoteException {

		this.setLayout(new BorderLayout());
		
		JLabel lblChatroom = new JLabel("ChatRoom");
		lblChatroom.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblChatroom, BorderLayout.NORTH);

		chatTextArea = new MessagePanelDisplay();
		this.add(chatTextArea, BorderLayout.CENTER);
		client.setOutput(this.chatTextArea);

		Box horizontalBox = Box.createHorizontalBox();
		this.add(horizontalBox, BorderLayout.SOUTH);

		JTextField msgTextField = new JTextField();
		horizontalBox.add(msgTextField);
		msgTextField.setColumns(10);

		JButton btnSend = new JButton("Send");
		horizontalBox.add(btnSend);

		Box verticalBox = Box.createVerticalBox();
		this.add(verticalBox, BorderLayout.EAST);

		JLabel lblUsers = new JLabel("Users :");
		verticalBox.add(lblUsers);

		JList<String> usersList = new JList<>(client.getSession().getAllUsers());

		JScrollPane scrollPane = new JScrollPane(usersList);
		verticalBox.add(scrollPane);
		// send a message with enter
		msgTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						client.sendMessage(msgTextField.getText());
						msgTextField.setText("");
					} catch (Exception ex) {
						ExceptionPopup.showError(ex);
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});
		// send a message with the button
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage(msgTextField.getText());
					msgTextField.setText(""); // clean the text field
				} catch (Exception ex) {
					ExceptionPopup.showError(ex);
				}
			}
		});

	}
}
