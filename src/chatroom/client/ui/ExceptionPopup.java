package chatroom.client.ui;

import javax.swing.*;

public class ExceptionPopup {

    /**
     * Open a popup to display the exception message
     *
     * @param exceptionError displayed exception
     */
    public static void showError(Exception exceptionError) {
        String errorMessage = "Message: " + exceptionError.getMessage();
        String title = "Exception: " + exceptionError.getClass().getName();
        showError(errorMessage, title);
    }

    private static void showError(String errorMessage, String title) {
        JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(String errorMessage) {
        showError(errorMessage, "Error!");
    }
}
