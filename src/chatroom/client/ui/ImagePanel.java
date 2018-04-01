package chatroom.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 160997660247771260L;

	private BufferedImage image;

	public ImagePanel(File file, Color color) {      
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			ExceptionPopup.showError(e);
		}
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
		float ratio = (float) 0.0;
		if (image.getHeight()<image.getWidth()) {
			ratio = ((float)image.getWidth())/((float)image.getHeight());
		}else {
			ratio = ((float)image.getHeight())/((float)image.getWidth());
		}
		g.drawImage(image, 0, 0, (int)(100*ratio), 100, this); 
	}

}