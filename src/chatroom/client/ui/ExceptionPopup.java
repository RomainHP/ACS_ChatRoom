package chatroom.client.ui;

import javax.swing.JOptionPane;

public class ExceptionPopup extends JOptionPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4956198879813629160L;

	public static void showError(Exception exceptionError) {
	    String errorMessage = "Message: " + exceptionError.getMessage();
	    String title = exceptionError.getClass().getName();
	    showError(errorMessage, title);
	}
	
	public static void showError(String errorMessage, String title) {
	    JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}  
	
	public static void showError(String errorMessage) {
	    showError(errorMessage, "Error!");
	}
}
