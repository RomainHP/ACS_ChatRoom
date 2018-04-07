package chatroom.client.ui;

import chatroom.client.Client;
import chatroom.server.Login;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.rmi.RemoteException;

public class MainFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -7487363104762069085L;

    private Client client;

    private JTabbedPane tabPane;

    public MainFrame(Login log, Client c) throws RemoteException {
        super("My Cat Room");
        this.setBounds(100, 100, 444, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.client = c;
        ImageIcon img = new ImageIcon("src/images/cat_icon.png");
        this.setIconImage(img.getImage());
        //tabpane
        tabPane = new JTabbedPane(JTabbedPane.TOP);
        tabPane.add(new LoginPanel(this, log, client), "Login");
        tabPane.add(new JButton("+"), "+");
        //add a new tab when click on '+' button
        tabPane.addChangeListener((ChangeEvent event) -> {
            if (tabPane.getSelectedIndex() == (tabPane.getTabCount() - 1)) {
                tabPane.setSelectedIndex(tabPane.getTabCount() - 2);
                tabPane.removeTabAt(tabPane.getTabCount() - 1);
                try {
                    tabPane.add(new LoginPanel(MainFrame.this, log, MainFrame.this.client), "Login");
                    tabPane.add(null, "+");
                } catch (Exception e1) {
                    ExceptionPopup.showError(e1);
                }
                tabPane.setSelectedIndex(tabPane.getTabCount() - 2);
            }
        });
        //close when right click
        tabPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    int index = tabPane.getSelectedIndex();
                    if (index >= 0 && index < (tabPane.getTabCount() - 1) && tabPane.getTabCount() > 2) {
                        Component compo = tabPane.getComponentAt(index);
                        if (compo instanceof ChatPanel) {
                            try {
                                ((ChatPanel) compo).getClient().disconnect();
                            } catch (IOException e) {
                                ExceptionPopup.showError(e);
                            }
                        } else if (compo instanceof LoginPanel) {
                            try {
                                ((LoginPanel) compo).getClient().disconnect();
                            } catch (IOException e) {
                                ExceptionPopup.showError(e);
                            }
                        }
                        tabPane.setSelectedIndex(index - 1);
                        tabPane.removeTabAt(index);
                    } else {
                        try {
                            client.disconnect();
                            System.exit(0);
                        } catch (IOException e) {
                            ExceptionPopup.showError(e);
                        }
                    }
                    tabPane.revalidate();
                }
            }
        });
        this.add(tabPane, BorderLayout.CENTER);

        //disconnect the client on close
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    client.disconnect();
                } catch (IOException e1) {
                    ExceptionPopup.showError(e1);
                }

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }
        });
    }

    /**
     * Change login to chat
     *
     * @param log   the login panel we change to chat panel
     * @param title the name of the chatroom
     * @throws RemoteException
     */
    public void changeView(LoginPanel log, String title) throws RemoteException {
        int index = 0;
        for (int i = 0; i < tabPane.getTabCount(); i++) {
            if (log.equals(tabPane.getComponentAt(i))) {
                index = i;
            }
        }
        this.tabPane.setSelectedIndex(index);
        this.tabPane.setComponentAt(index, new ChatPanel(title, this.client));
        this.tabPane.setTitleAt(index, title);
    }
}
