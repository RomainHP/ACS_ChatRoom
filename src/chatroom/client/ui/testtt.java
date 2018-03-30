package chatroom.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class testtt {

	private JFrame frame;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testtt window = new testtt();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public testtt() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 444, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Box verticalBox = Box.createVerticalBox();
		frame.getContentPane().add(verticalBox, BorderLayout.WEST);

		JLabel lblPseudo = new JLabel("Nickname :");
		verticalBox.add(lblPseudo);

		JTextField pseudoTextField = new JTextField();
		verticalBox.add(pseudoTextField);
		pseudoTextField.setColumns(10);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblLogin, BorderLayout.NORTH);

		JButton btnConfirm = new JButton("Confirm");
		frame.getContentPane().add(btnConfirm, BorderLayout.SOUTH);

		JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabPane, BorderLayout.CENTER);

		Box chatroomScrollPane = Box.createVerticalBox();
		tabPane.addTab("Join", null, chatroomScrollPane, null);

		JLabel lblChatroom = new JLabel("ChatRoom :");
		chatroomScrollPane.add(lblChatroom);

		JList<String> chatroomsList = new JList<>();

		JScrollPane scrollPane = new JScrollPane(chatroomsList);
		chatroomScrollPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		scrollPane.setColumnHeaderView(btnNewButton);

		Box verticalBox_2 = Box.createVerticalBox();
		tabPane.addTab("Create", null, verticalBox_2, null);

		JLabel chatroomLabel = new JLabel("ChatRoom :");
		verticalBox_2.add(chatroomLabel);

		JTextField chatroomTextField = new JTextField();
		verticalBox_2.add(chatroomTextField);
		chatroomTextField.setColumns(10);

		JLabel lblPassword = new JLabel("Password :");
		verticalBox_2.add(lblPassword);
		
		passwordField = new JPasswordField();
		verticalBox_2.add(passwordField);
	}

}
