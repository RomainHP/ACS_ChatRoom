package chatroom.client.ui.display;

import chatroom.client.message.ImageMessage;
import chatroom.client.message.Message;
import chatroom.client.message.SoundMessage;
import chatroom.client.message.SystemMessage;
import chatroom.client.ui.ChatPanel;
import chatroom.client.ui.ExceptionPopup;
import chatroom.client.ui.ImagePanel;
import chatroom.client.ui.SoundButton;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * JPanel used to represent the chat It can display image and simple text
 */
public class MessagePanelDisplay extends JPanel implements Display {

    private static final long serialVersionUID = -1059871847589145969L;

    private boolean color = true;

    private final ChatPanel chatpan;

    private JScrollPane scroll;

    public MessagePanelDisplay(ChatPanel parent) {
        this.chatpan = parent;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    private void addPrivate(Box box) {
        JLabel privLabel = new JLabel("(private)");
        privLabel.setFont(privLabel.getFont().deriveFont(~Font.BOLD));
        privLabel.setFont(privLabel.getFont().deriveFont(Font.ITALIC));
        box.add(privLabel);
    }

    /**
     * Add a text to the panel
     *
     * @param from client who send the message
     * @param msg  message
     */
    private void addText(String from, String msg, boolean isPrivate) {
        Box box = Box.createHorizontalBox();
        if (color) {
            box.setBackground(Color.LIGHT_GRAY);
        }
        JLabel nick = new JLabel("[" + from + "] : ");
        JLabel message = new JLabel(msg);
        message.setFont(message.getFont().deriveFont(~Font.BOLD));
        //if the message is private it's in italic
        if (isPrivate) message.setFont(message.getFont().deriveFont(Font.ITALIC));
        box.add(nick);
        box.add(message);
        box.add(Box.createHorizontalGlue());
        if (isPrivate) {
            this.addPrivate(box);
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
     * @param msg  message
     */
    private void addImage(String from, BufferedImage msg, boolean isPrivate) {
        Box box = Box.createHorizontalBox();
        if (color) {
            box.setBackground(Color.LIGHT_GRAY);
        }
        box.setOpaque(true);
        color = !color;
        JLabel nick = new JLabel("[" + from + "] : ");
        ImagePanel img = new ImagePanel(msg, box.getBackground());
        box.add(nick);
        box.add(img);
        box.add(Box.createHorizontalGlue());
        if (isPrivate) {
            this.addPrivate(box);
        }
        this.add(box);
        this.revalidate();
    }

    /**
     * Add an image to the panel
     *
     * @param from client who send the message
     * @param msg  message
     */
    private void addSound(String from, AudioInputStream msg, boolean isPrivate) throws IOException, LineUnavailableException {
        Box box = Box.createHorizontalBox();
        if (color) {
            box.setBackground(Color.LIGHT_GRAY);
        }
        box.setOpaque(true);
        color = !color;
        JLabel nick = new JLabel("[" + from + "] : ");
        SoundButton button = new SoundButton(msg);
        box.add(nick);
        box.add(button);
        box.add(Box.createHorizontalGlue());
        if (isPrivate) {
            this.addPrivate(box);
        }
        this.add(box);
        this.revalidate();
    }

    /**
     * Write in the panel the message sent
     * @param aMsg the message to write
     */
    @Override
    public void write(Message aMsg) {
        if (null != aMsg) {
            if (aMsg instanceof SoundMessage) {
                try {
                    this.addSound(aMsg.getNick(), ((SoundMessage) aMsg).getSound(), aMsg.getIsPrivate());
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                    ExceptionPopup.showError(e);
                }
            } else if (aMsg instanceof SystemMessage) {
                this.addText(aMsg.getNick(), aMsg.toString(), aMsg.getIsPrivate());
                try {
                    chatpan.actualizeUsers();
                } catch (RemoteException e) {
                    ExceptionPopup.showError(e);
                }
            } else if (aMsg instanceof ImageMessage) {
                try {
                    this.addImage(aMsg.getNick(), ((ImageMessage) aMsg).getImage(), aMsg.getIsPrivate());
                } catch (IOException e) {
                    ExceptionPopup.showError(e);
                }
            } else { //Message
                this.addText(aMsg.getNick(), aMsg.toString(), aMsg.getIsPrivate());
            }
            if (scroll != null) {
                this.revalidate();
                scroll.revalidate();
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
            }
        }
    }

    public void setScrollPane(JScrollPane scroll) {
        this.scroll = scroll;
    }

}
