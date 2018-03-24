package chatroom.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class Frame2 {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame2 window = new Frame2();
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
	public Frame2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblChatroom = new JLabel("ChatRoom");
		lblChatroom.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblChatroom, BorderLayout.NORTH);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
		
		Box horizontalBox = Box.createHorizontalBox();
		frame.getContentPane().add(horizontalBox, BorderLayout.SOUTH);
		
		textField = new JTextField();
		horizontalBox.add(textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		horizontalBox.add(btnSend);
		
		Box verticalBox = Box.createVerticalBox();
		frame.getContentPane().add(verticalBox, BorderLayout.EAST);
		
		JLabel lblUsers = new JLabel("Users :");
		verticalBox.add(lblUsers);
		
		JScrollPane scrollPane = new JScrollPane();
		verticalBox.add(scrollPane);
	}

}
