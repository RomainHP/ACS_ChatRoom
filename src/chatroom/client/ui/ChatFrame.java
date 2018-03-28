package chatroom.client.ui;

import chatroom.client.Client;
import chatroom.client.JTextAreaOutputStream;
import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.OutputStream;
import java.io.PrintStream;

public class ChatFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -301099811934446277L;

    JTextArea chatTextArea;

    public ChatFrame(String title, Client client) throws RemoteException {
        super("Chatroom : " + title);
        this.setBounds(100, 100, 450, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblChatroom = new JLabel("ChatRoom");
        lblChatroom.setHorizontalAlignment(SwingConstants.CENTER);
        this.getContentPane().add(lblChatroom, BorderLayout.NORTH);

        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        this.getContentPane().add(chatTextArea, BorderLayout.CENTER);

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

        JList<String> usersList = new JList<>(client.getSession().getAllUsers());

        JScrollPane scrollPane = new JScrollPane(usersList);
        verticalBox.add(scrollPane);
        //send a message with enter
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
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
        //send a message with the button
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.sendMessage(msgTextField.getText());
                    msgTextField.setText(""); //clean the text field
                } catch (Exception ex) {
                    ExceptionPopup.showError(ex);
                }
            }
        });
    }

    public OutputStream getOutput() {
        PrintStream printStream = new PrintStream(new JTextAreaOutputStream(chatTextArea));
        return printStream;
    }
}
