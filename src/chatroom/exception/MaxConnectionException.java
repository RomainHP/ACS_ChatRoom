package chatroom.exception;

public class MaxConnectionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -2778459810615711447L;

    public MaxConnectionException() {
        super("Max connection number reached");
    }
}
