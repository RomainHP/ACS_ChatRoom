package chatroom.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;

public class Frame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
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
	public Frame() {
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
		
		textField = new JTextField();
		verticalBox.add(textField);
		textField.setColumns(10);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblLogin, BorderLayout.NORTH);
		
		JButton btnConfirm = new JButton("Confirm");
		frame.getContentPane().add(btnConfirm, BorderLayout.SOUTH);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		Box verticalBox_1 = Box.createVerticalBox();
		tabbedPane.addTab("Join", null, verticalBox_1, null);
		
		JLabel lblChatroom = new JLabel("ChatRoom :");
		verticalBox_1.add(lblChatroom);
		
		JScrollPane scrollPane = new JScrollPane();
		verticalBox_1.add(scrollPane);
		
		JList list_1 = new JList();
		verticalBox_1.add(list_1);
		
		Box verticalBox_2 = Box.createVerticalBox();
		tabbedPane.addTab("Create", null, verticalBox_2, null);
		
		JLabel label = new JLabel("ChatRoom :");
		verticalBox_2.add(label);
		
		textField_1 = new JTextField();
		verticalBox_2.add(textField_1);
		textField_1.setColumns(10);
	}

}
