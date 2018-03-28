package chatroom.client;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.Serializable;

public class Message implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3659547225566522546L;

	TypeMessage type;
    
    String message;
    
    Image image;
    
    public Message(String msg, String nick){
        this.message = nick + " : " + msg + "\n";
        this.type = TypeMessage.STRING;
    }
    
    public Message(Image image){
        this.image = image;
        this.type = TypeMessage.IMAGE;
    }
    
    /**
     * Used to display the message on OutputStream
     * @return tab of type
     */
    public byte[] getMessage(){
        if (this.type==TypeMessage.IMAGE) {
        	BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            // Draw the image on to the buffered image
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
        	WritableRaster raster = bimage.getRaster();
        	DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
        	return data.getData();
        }else {
        	return message.getBytes();
        }
    }
}
