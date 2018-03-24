package chatroom.client.ui;

import java.awt.Dimension;

import javax.swing.*;

public class ChatFrame extends JFrame{

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
	
	public ChatFrame(String title) {
		super("Chatroom : " + title);
		this.setPreferredSize(new Dimension(400,300));
		this.textToSend = new JTextArea();
		this.textReceived = new JTextArea();
		this.users = new JList<>();
		this.validate = new JButton("Validate");
	}

}
