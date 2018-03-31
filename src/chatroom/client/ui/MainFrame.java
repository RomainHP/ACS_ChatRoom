package chatroom.client.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import chatroom.client.Client;
import chatroom.server.Login;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7487363104762069085L;
	
	private Client client;

	public MainFrame(Login log, Client c) throws RemoteException {
		super("Login");
		this.setBounds(100, 100, 444, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.client = c;
		
		this.add(new LoginPanel(this, log, client), BorderLayout.CENTER);
		
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
	
	public void changeView(String title) throws RemoteException {
		this.setTitle(title);
		this.setContentPane(new ChatPanel(this.client));
		this.revalidate(); 
	}
}
