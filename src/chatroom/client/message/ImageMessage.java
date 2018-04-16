package chatroom.client.message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author rcharpen
 */
public class ImageMessage extends Message {

    private final File image;

    public ImageMessage(File file, String nick) {
        super(file.toString(), nick);
        //convert file to image
        this.image = file;
    }

    public ImageMessage(File file, String nick, boolean priv) {
        super(file.toString(), nick, priv);
        //convert file to image
        this.image = file;
    }

    public BufferedImage getImage() throws IOException {
        return ImageIO.read(image);
    }
}
