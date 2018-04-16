package chatroom.client.ui;

import chatroom.client.Client;
import chatroom.server.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SelectServerFrame extends JFrame {

    public SelectServerFrame(){
        super("Write server adress");
        this.add(new JLabel("Write server adress below"), BorderLayout.NORTH);
        JTextField text = new JTextField();
        JButton validate = new JButton("Validate");
        validate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    // Login
                    Client.server_name = text.getText();
                    String url = "rmi://" + Client.server_name + "/login";
                    Login log = (Login) Naming.lookup(url);
                    Client client = new Client(log);
                    SelectServerFrame.this.dispose();
                    MainFrame frame = new MainFrame(log, client);
                    frame.setVisible(true);
                } catch (NotBoundException | MalformedURLException | RemoteException e) {
                    ExceptionPopup.showError(e);
                }
            }
        });
        this.add(text);
        this.add(validate, BorderLayout.SOUTH);
        this.pack();
    }
}
