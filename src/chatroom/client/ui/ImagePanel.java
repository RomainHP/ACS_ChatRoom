package chatroom.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 160997660247771260L;

    private final BufferedImage image;

    public ImagePanel(BufferedImage image, Color color) {
        this.image = image;
        this.setPreferredSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(100, 100));
        this.setBackground(color);
        this.setOpaque(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                ImageIcon icon = new ImageIcon(image);
                JOptionPane.showMessageDialog(
                        null,
                        "",
                        "Image (Real size)", JOptionPane.INFORMATION_MESSAGE,
                        icon);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        float ratio;
        if (image.getHeight() < image.getWidth()) {
            ratio = ((float) image.getWidth()) / ((float) image.getHeight());
        } else {
            ratio = ((float) image.getHeight()) / ((float) image.getWidth());
        }
        g.drawImage(image, 0, 0, (int) (100 * ratio), 100, this);
    }

}
