package chatroom.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import chatroom.client.Client;
import chatroom.exception.*;
import chatroom.server.Login;

/**
 * Login interface
 */
public class LoginPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -301099811934446277L;

    private JList<String> chatroomsList;

    private JButton btnConfirm;

    private JButton actualizeButton;

    private Client client;

    public LoginPanel(MainFrame frame, Login log, Client client) throws RemoteException {
        this.setLayout(new BorderLayout());

        this.client = client;

        Box verticalBox = Box.createVerticalBox();
        this.add(verticalBox, BorderLayout.WEST);

        JLabel lblPseudo = new JLabel("Nickname :");
        verticalBox.add(lblPseudo);

        JTextField pseudoTextField = new JTextField();
        verticalBox.add(pseudoTextField);
        pseudoTextField.setColumns(10);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblLogin, BorderLayout.NORTH);

        btnConfirm = new JButton("Confirm");
        this.add(btnConfirm, BorderLayout.SOUTH);

        JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
        this.add(tabPane, BorderLayout.CENTER);

        Box chatroomScrollPane = Box.createVerticalBox();
        tabPane.addTab("Join", null, chatroomScrollPane, null);

        Box selectBox = Box.createHorizontalBox();

        JLabel lblChatroom = new JLabel("Select a ChatRoom :");
        selectBox.add(lblChatroom);

        selectBox.add(Box.createHorizontalGlue());

        actualizeButton = new JButton("Actualize");
        selectBox.add(actualizeButton);

        chatroomScrollPane.add(selectBox, BorderLayout.NORTH);

        this.chatroomsList = new JList<>(log.getAllChatRoom());

        JScrollPane scrollPane = new JScrollPane(chatroomsList);
        chatroomScrollPane.add(scrollPane);

        Box verticalBox_2 = Box.createVerticalBox();
        tabPane.addTab("Create", null, verticalBox_2, null);

        JLabel chatroomLabel = new JLabel("ChatRoom :");
        verticalBox_2.add(chatroomLabel);

        JTextField chatroomTextField = new JTextField();
        verticalBox_2.add(chatroomTextField);
        chatroomTextField.setColumns(10);

        JLabel lblPassword = new JLabel("Password :");
        verticalBox_2.add(lblPassword);

        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setColumns(10);
        verticalBox_2.add(passwordTextField);

        JLabel lblMaxUsers = new JLabel("Max Users :");
        verticalBox_2.add(lblMaxUsers);

        IncrementPanel maxUsersPanel = new IncrementPanel();
        verticalBox_2.add(maxUsersPanel);

        // actualize the list of chatroom
        actualizeButton.addActionListener((ActionEvent arg0) -> {
            try {
                LoginPanel.this.chatroomsList.setListData(log.getAllChatRoom());
            } catch (RemoteException e) {
                ExceptionPopup.showError(e);
            }
        });

        // launch the chat
        btnConfirm.addActionListener((ActionEvent arg0) -> {
            try {
                // nickname
                String pseudo = pseudoTextField.getText();
                if (!Client.verifyName(pseudo)) {
                    throw new UncorrectNameException("nickname");
                }
                // chat
                String chat;
                // password
                String password = "";
                // number max of users
                int max_users=10;
                // tab choose a chatroom
                if (tabPane.getSelectedIndex() == 0) {
                    if (chatroomsList.isSelectionEmpty()) {
                        return;
                    }
                    chat = chatroomsList.getSelectedValue();
                    if (log.isPrivateChatroom(chat)) {
                        password = PasswordPopup.askPassword();
                    }
                    // tab create a chatroom
                } else {
                    chat = chatroomTextField.getText();
                    password = passwordTextField.getText();
                    if (!Client.verifyName(chat)) {
                        throw new UncorrectNameException("chat");
                    }
                    max_users = maxUsersPanel.getVal();
                }
                client.setNickname(pseudo);
                // no password
                if (password.trim().length() == 0) {
                    if (max_users>0){
                        client.connect(pseudo,chat,max_users);
                    }else{
                        throw new IncorrectMaxUsers();
                    }
                    // password
                } else {
                    if (!Client.verifyName(password)) {
                        throw new UncorrectNameException("password");
                    }
                    if (max_users>0){
                        client.connect(pseudo, chat, password, max_users);
                    }else{
                        throw new IncorrectMaxUsers();
                    }
                }
                frame.changeView(LoginPanel.this, chat);
            } catch (MaxConnectionException | WrongPasswordException | NicknameNotAvailableException | NotBoundException | UncorrectNameException | IncorrectMaxUsers | IOException e) {
                ExceptionPopup.showError(e);
            }
        });
    }

    public void setConfirmAction(ActionListener act) {
        this.btnConfirm.addActionListener(act);
    }

    public void setActualizeAction(ActionListener act) {
        this.actualizeButton.addActionListener(act);
    }

    public Client getClient() {
        return this.client;
    }
}
