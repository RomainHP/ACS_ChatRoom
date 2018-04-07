package chatroom.exception;

public class NotFoundUserException extends Exception {

    public NotFoundUserException(){
        super("The user is not found");
    }

    public NotFoundUserException(String user){
        super("The user " + user + " is not found");
    }
}
