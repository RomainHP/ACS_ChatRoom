package chatroom.client.ui;

import chatroom.client.Client;
import chatroom.client.ui.display.MessagePanelDisplay;
import chatroom.exception.NotFoundUserException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * Chat interface (display a chatroom)
 */
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
        usersList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                    usersList.clearSelection();
                }
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}
            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        JScrollPane scrollPane = new JScrollPane(usersList);
        verticalBox.add(scrollPane);
        // send a message with enter
        msgTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        if (usersList.isSelectionEmpty()) {
                            client.sendMessage(msgTextField.getText());
                        } else {
                            client.sendMessage(msgTextField.getText(), usersList.getSelectedValue());
                        }
                        msgTextField.setText("");
                    } catch (Exception ex) {
                        ExceptionPopup.showError(ex);
                    }
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}

        });
        // send a message with the button
        btnSend.addActionListener((ActionEvent e) -> {
            try {
                if (usersList.isSelectionEmpty()) {
                    client.sendMessage(msgTextField.getText());
                } else {
                    client.sendMessage(msgTextField.getText(), usersList.getSelectedValue());
                }
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
            c.setAcceptAllFileFilterUsed(false);
            //Displaying Filechooser
            int rVal = c.showOpenDialog(new JPanel());
            if (rVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = c.getSelectedFile();
                try {
                    if (usersList.isSelectionEmpty()) {
                        client.sendImageMessage(selectedFile);
                    } else {
                        client.sendImageMessage(selectedFile, usersList.getSelectedValue());
                    }
                } catch (IOException | NotFoundUserException e1) {
                    ExceptionPopup.showError(e1);
                }
            }
        });

        //Send an image
        btnSound.addActionListener((ActionEvent e) -> {
            JFileChooser c = new JFileChooser();
            //Setting Up The Filter
            FileNameExtensionFilter soundFilter = new FileNameExtensionFilter(
                    "Wav files", "wav");
            //Attaching Filter to JFileChooser object
            c.setFileFilter(soundFilter);
            c.setAcceptAllFileFilterUsed(false);
            //Displaying Filechooser
            int rVal = c.showOpenDialog(new JPanel());
            if (rVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = c.getSelectedFile();
                try {
                    if (usersList.isSelectionEmpty()) {
                        client.sendSoundMessage(selectedFile);
                    } else {
                        client.sendSoundMessage(selectedFile, usersList.getSelectedValue());
                    }
                } catch (IOException | UnsupportedAudioFileException | NotFoundUserException e1) {
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
