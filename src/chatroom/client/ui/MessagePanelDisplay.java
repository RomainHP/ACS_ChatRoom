package chatroom.client.ui;

import chatroom.client.Message;

public class MessagePanelDisplay extends MessagePanel implements Display {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6907288588243414121L;

	@Override
	public void write(Message aMsg) {
		switch (aMsg.getType()) {
			case SYSTEM:
				//TODO
				break;
			case MESSAGE:
				break;
			case IMAGE:
				break;
			default: 
				break;
		}
	}

}
