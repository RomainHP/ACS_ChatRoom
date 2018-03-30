package chatroom.client;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
		this.message = "[" + nick + "] : " + msg + "\n";
		this.type = TypeMessage.MESSAGE;
	}

	public Message(Image image){
		this.image = image;
		this.message = "";
		this.type = TypeMessage.IMAGE;
	}

	public Message(String msg) {
		this.message = msg + "\n";
		this.type = TypeMessage.SYSTEM;
	}

	/**
	 * Convert the object to an array of byte
	 * @return an array of byte corresponding this
	 * @throws IOException 
	 */
	/*public byte[] getByteMessage() throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);
		out.writeObject(this);
		out.close();
		return bos.toByteArray();
	}*/
	
	public byte[] getByteMessage() {
		return this.message.getBytes();
	}

	public TypeMessage getType() {
		return this.type;
	}

	public String getMessage() {
		return this.message;
	}

}
