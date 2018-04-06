package chatroom.client.ui;

import chatroom.client.Client;
import chatroom.client.ui.display.MessagePanelDisplay;

import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ChatPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -301099811934446277L;

    private final Client client;

    private final JList<String> usersList;

    public ChatPanel(String name, Client client) throws RemoteException {
        this.setLayout(new BorderLayout());

        this.client = client;

        JLabel lblChatroom = new JLabel("ChatRoom " + name);
        lblChatroom.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblChatroom, BorderLayout.NORTH);

        MessagePanelDisplay chatTextArea = new MessagePanelDisplay(this);
        JScrollPane scroll = new JScrollPane(chatTextArea);
        chatTextArea.setScrollPane(scroll);
        this.add(scroll, BorderLayout.CENTER);
        client.setOutput(chatTextArea);

        Box horizontalBox = Box.createHorizontalBox();
        this.add(horizontalBox, BorderLayout.SOUTH);

        JTextField msgTextField = new JTextField();
        horizontalBox.add(msgTextField);
        msgTextField.setColumns(10);

        JButton btnSend = new JButton("Send");
        horizontalBox.add(btnSend);

        JButton btnImg = new JButton("Img");
        horizontalBox.add(btnImg);

        JButton btnSound = new JButton("Sound");
        horizontalBox.add(btnSound);

        Box verticalBox = Box.createVerticalBox();
        this.add(verticalBox, BorderLayout.EAST);

        JLabel lblUsers = new JLabel("Users :");
        verticalBox.add(lblUsers);

        usersList = new JList<>(client.getSession().getAllUsers());

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
        btnSend.addActionListener((ActionEvent e) -> {
            try {
                client.sendMessage(msgTextField.getText());
                msgTextField.setText(""); // clean the text field
            } catch (Exception ex) {
                ExceptionPopup.showError(ex);
            }
        });

        //Send an image
        btnImg.addActionListener((ActionEvent e) -> {
            JFileChooser c = new JFileChooser();
            //Setting Up The Filter
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                    "Image files", ImageIO.getReaderFileSuffixes());
            //Attaching Filter to JFileChooser object
            c.setFileFilter(imageFilter);
            //Displaying Filechooser
            int rVal = c.showOpenDialog(new JPanel());
            if (rVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = c.getSelectedFile();
                try {
                    client.sendImageMessage(selectedFile);
                } catch (IOException e1) {
                    ExceptionPopup.showError(e1);
                }
            }
        });

        //Send an image
        btnSound.addActionListener((ActionEvent e) -> {
            JFileChooser c = new JFileChooser();
            //Setting Up The Filter
            FileNameExtensionFilter soundFilter = new FileNameExtensionFilter(
                    "MP3 files", Arrays.toString(AudioSystem.getAudioFileTypes()));
            //TODO file filter wav
            //Attaching Filter to JFileChooser object
            //c.setFileFilter((javax.swing.filechooser.FileFilter) soundFilter);
            //Displaying Filechooser
            int rVal = c.showOpenDialog(new JPanel());
            if (rVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = c.getSelectedFile();
                try {
                    client.sendSoundMessage(c.getSelectedFile());
                } catch (IOException | UnsupportedAudioFileException e1) {
                    ExceptionPopup.showError(e1);
                }
            }
        });
    }

    public Client getClient() {
        return this.client;
    }

    public void actualizeUsers() throws RemoteException {
        this.usersList.setListData(this.client.getSession().getAllUsers());
    }
}
