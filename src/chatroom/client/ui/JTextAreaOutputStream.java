package chatroom.client.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

import chatroom.client.Message;

public class JTextAreaOutputStream extends OutputStream {

	private JTextArea textArea;

	public JTextAreaOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(byte[] b) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Message o = (Message)in.readObject();
			switch (o.getType()) {
			case SYSTEM:
				this.textArea.append(o.getMessage());
				break;
			case IMAGE:
				break;
			case MESSAGE:
				this.textArea.append(o.getMessage());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			ExceptionPopup.showError(e);
		}
		in.close();
	}

	@Override
	public void write(int b) throws IOException {
		// redirects data to the text area 
        textArea.append(String.valueOf((char)b)); 
        // scrolls the text area to the end of data 
        textArea.setCaretPosition(textArea.getDocument().getLength()); 
	}
}