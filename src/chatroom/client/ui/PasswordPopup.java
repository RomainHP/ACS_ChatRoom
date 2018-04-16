package chatroom.client.ui;

import javax.swing.*;

class PasswordPopup {

    /**
     * Create a Popup to ask the chatroom password
     *
     * @return password given by the user
     */
    public static String askPassword() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password : ");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Password",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if (option == 0) // pressing OK button
        {
            char[] password = pass.getPassword();
            return new String(password);
        }
        // cancel
        return "";
    }

}
