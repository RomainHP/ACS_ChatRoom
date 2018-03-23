package client.ui;

import java.awt.Dimension;

import javax.swing.*;

public class LoginFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -301099811934446277L;
	
	JTextArea nickname;
	
	JTextArea chatroom;
	
	JList<JLabel> chatrooms;
	
	JButton confirm;
	
	public LoginFrame() {
		super("Login");
		this.setPreferredSize(new Dimension(400,300));
	}

}
