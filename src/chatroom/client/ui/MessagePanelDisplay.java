package chatroom.client.ui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chatroom.client.Message;

/**
 * JPanel used to represent the chat It can display image and simple text
 */
public class MessagePanelDisplay extends JPanel implements Display {

    /**
     *
     */
    private static final long serialVersionUID = -1059871847589145969L;

    private boolean color = true;

    private ChatPanel chatpan;

    public MessagePanelDisplay(ChatPanel parent) {
        this.chatpan = parent;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    /**
     * Add a text to the panel
     *
     * @param from client who send the message
     * @param msg message
     */
    private void addText(String from, String msg) {
        Box box = Box.createHorizontalBox();
        JLabel nick = new JLabel("[" + from + "] :     ");
        JLabel message = new JLabel(msg);
        message.setFont(message.getFont().deriveFont(~Font.BOLD));
        box.add(nick);
        box.add(message);
        box.add(Box.createHorizontalGlue());
        if (color) {
            box.setBackground(Color.LIGHT_GRAY);
        }
        box.setOpaque(true);
        color = !color;
        this.add(box);
        this.revalidate();
    }

    /**
     * Add an image to the panel
     *
     * @param from client who send the message
     * @param msg message
     */
    private void addImage(String from, File msg) {
        Box box = Box.createHorizontalBox();
        if (color) {
            box.setBackground(Color.LIGHT_GRAY);
        }
        box.setOpaque(true);
        color = !color;
        JLabel nick = new JLabel("[" + from + "] :     ");
        ImagePanel img = new ImagePanel(msg, box.getBackground());
        box.add(nick);
        box.add(img);
        box.add(Box.createHorizontalGlue());
        this.add(box);
        this.revalidate();
    }

    @Override
    public void write(Message aMsg) {
        if (null != aMsg.getType()) {
            switch (aMsg.getType()) {
                case SYSTEM:
                    this.addText(aMsg.getNick(), aMsg.toString());
                    try {
                        chatpan.actualizeUsers();
                    } catch (RemoteException e) {
                        ExceptionPopup.showError(e);
                    }
                    break;
                case IMAGE:
                    this.addImage(aMsg.getNick(), aMsg.getImage());
                    break;
                default:
                    this.addText(aMsg.getNick(), aMsg.toString());
                    break;
            }
        }
    }

}
