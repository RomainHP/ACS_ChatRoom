package chatroom.exception;

public class NotEnoughArgumentsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3952099913740214946L;

	public NotEnoughArgumentsException() {
		super("Not enough arguments");
	}
}
