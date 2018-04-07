package chatroom.client.message;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author rcharpen
 */
public class ImageMessage extends Message {

    private File image;

    public ImageMessage(File file, String nick) throws IOException {
        super(file.toString(), nick);
        //convert file to image
        this.image = file;
    }

    public ImageMessage(File file, String nick, boolean priv) throws IOException {
        super(file.toString(), nick, priv);
        //convert file to image
        this.image = file;
    }

    public BufferedImage getImage() throws IOException {
        return ImageIO.read(image);
    }
}
