package chatroom.client.message;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author rcharpen
 */
public class ImageMessage extends Message {

    private transient BufferedImage image;

    public ImageMessage(File file, String nick) throws IOException {
        super(file.toString(), nick);
        //convert file to image
        this.image = ImageIO.read(file);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(1);
        ImageIO.write(image, "png", out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        final int imageCount = in.readInt();
        image = ImageIO.read(in);
    }

}
