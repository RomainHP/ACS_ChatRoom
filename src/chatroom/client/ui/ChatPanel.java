package chatroom.client.ui;

import chatroom.client.Client;
import chatroom.server.Session;

import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class ChatPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -301099811934446277L;

    private final MessagePanelDisplay chatTextArea;

    private final String name;

    private final Client client;
    
    private final Session session;

    private final JList<String> usersList;

    public ChatPanel(String name, Client client, Session session) throws RemoteException {
        this.setLayout(new BorderLayout());

        this.session = session;
        this.name = name;
        this.client = client;

        JLabel lblChatroom = new JLabel("ChatRoom " + name);
        lblChatroom.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblChatroom, BorderLayout.NORTH);

        chatTextArea = new MessagePanelDisplay(this);

        JScrollPane scroll = new JScrollPane(chatTextArea);
        chatTextArea.setScrollPane(scroll);
        this.add(scroll, BorderLayout.CENTER);
        client.setOutput(this.session,this.chatTextArea);


        this.add(new JScrollPane(chatTextArea), BorderLayout.CENTER);
        Session clientSession = client.getSession();
        client.setOutput(clientSession, this.chatTextArea);


        Box horizontalBox = Box.createHorizontalBox();
        this.add(horizontalBox, BorderLayout.SOUTH);

        JTextField msgTextField = new JTextField();
        horizontalBox.add(msgTextField);
        msgTextField.setColumns(10);

        JButton btnSend = new JButton("Send");
        horizontalBox.add(btnSend);

        JButton btnImg = new JButton("Img");
        horizontalBox.add(btnImg);

        Box verticalBox = Box.createVerticalBox();
        this.add(verticalBox, BorderLayout.EAST);

        JLabel lblUsers = new JLabel("Users :");
        verticalBox.add(lblUsers);
        
        this.usersList = new JList<>(this.session.getAllUsers());

        JScrollPane scrollPane = new JScrollPane(usersList);
        verticalBox.add(scrollPane);
        // send a message with enter
        msgTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        client.sendMessage(session, msgTextField.getText());
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
        btnSend.addActionListener((ActionEvent e) -> {
            try {
                client.sendMessage(session, msgTextField.getText());
                msgTextField.setText(""); // clean the text field
            } catch (Exception ex) {
                ExceptionPopup.showError(ex);
            }
        });

        btnImg.addActionListener((ActionEvent e) -> {
            JFileChooser c = new JFileChooser();
            //Setting Up The Filter
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                    "Image files", ImageIO.getReaderFileSuffixes());
            //Attaching Filter to JFileChooser object
            c.setFileFilter((javax.swing.filechooser.FileFilter) imageFilter);
            //Displaying Filechooser
            int rVal = c.showOpenDialog(new JPanel());
            if (rVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = c.getSelectedFile();
                try {
                    client.sendMessage(session, selectedFile);
                } catch (IOException e1) {
                    ExceptionPopup.showError(e1);
                }
            }
        });
    }

    public Client getClient() {
        return this.client;
    }
    
    public Session getSession(){
        return this.session;
    }
    
    public void actualizeUsers() throws RemoteException {
        this.usersList.setListData(this.session.getAllUsers());
    }
}
