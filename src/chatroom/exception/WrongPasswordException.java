package chatroom.exception;

public class WrongPasswordException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -3952099913740214946L;

    public WrongPasswordException() {
        super("Wrong password");
    }
}
