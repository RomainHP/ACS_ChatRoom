package chatroom.client.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncrementPanel extends JPanel {

    private JTextField text;

    public IncrementPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        text = new JTextField("10");
        text.setEditable(false);

        JButton buttonPlus = new JButton("+");
        buttonPlus.setActionCommand("+");

        JButton buttonMoins = new JButton("-");
        buttonMoins.setActionCommand("-");

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int val = Integer.parseInt(text.getText());
                switch (e.getActionCommand()) {
                    case "+":
                        text.setText(val+1+"");
                        break;
                    case "-":
                        text.setText(val-1+"");
                        break;
                    default:
                        break;
                }
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

    public int getVal(){
        int val = Integer.parseInt(this.text.getText());
        return val;
    }
}
