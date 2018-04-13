package chatroom.exception;

public class IncorrectMaxUsers extends Exception {

    public IncorrectMaxUsers() {
        super("Max users number need to be > 0");
    }
}
