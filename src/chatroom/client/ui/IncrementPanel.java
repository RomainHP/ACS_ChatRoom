package chatroom.client.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Panel containing a textfield with 2 buttons to do ++ or --
 */
public class IncrementPanel extends JPanel {

    private JTextField text;

    public IncrementPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        text = new JTextField("10");
        text.setEditable(false);

        JButton buttonPlus = new JButton("+");
        buttonPlus.setActionCommand("+");

        JButton buttonMoins = new JButton("-");
        buttonMoins.setActionCommand("-");

        ActionListener actionListener = e -> {
            int val = Integer.parseInt(text.getText());
            switch (e.getActionCommand()) {
                case "+":
                    text.setText(val + 1 + "");
                    break;
                case "-":
                    if (val > 1) {
                        text.setText(val - 1 + "");
                    }
                    break;
                default:
                    break;
            }
        };

        buttonPlus.addActionListener(actionListener);
        buttonMoins.addActionListener(actionListener);

        Box box = Box.createVerticalBox();
        box.add(buttonPlus);
        box.add(buttonMoins);

        this.add(text);
        this.add(box);

    }

    public int getVal() {
        return Integer.parseInt(this.text.getText());
    }
}
