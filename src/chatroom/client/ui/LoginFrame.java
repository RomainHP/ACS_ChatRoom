package chatroom.client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class LoginFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -301099811934446277L;
	
	/*JTextArea nickname;
	
	JTextArea chatroom;
	
	JList<String> chatroomList;
	
	JScrollPane chatroomListScoller;
	
	JButton confirm;
	
	public LoginFrame(String[] chats) {
		super("Login");
		
		this.nickname = new JTextArea(10, 10);
		this.chatroom = new JTextArea(10, 10);
		this.chatroomList = new JList<>(chats);
		this.chatroomListScoller = new JScrollPane(this.chatroomList);
		this.chatroomListScoller.setPreferredSize(new Dimension(250, 80));
		this.confirm = new JButton("Confirm");
		
		//Add
		this.add(this.nickname, BorderLayout.WEST);
		this.add(this.chatroomListScoller, BorderLayout.EAST);
		this.add(this.confirm, BorderLayout.SOUTH);
		
		this.setPreferredSize(new Dimension(400,300));
		this.pack();
	}*/
	
	public LoginFrame(String[] chats) {
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
	}

}
