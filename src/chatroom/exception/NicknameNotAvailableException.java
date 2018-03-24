package chatroom.exception;

public class NicknameNotAvailableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3952099913740214946L;

	public NicknameNotAvailableException() {
		super("Nickname not available");
	}
}
