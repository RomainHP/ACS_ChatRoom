package chatroom.exception;


public class NotAllowedPasswordException extends Exception{
    public NotAllowedPasswordException(){
        super("Password can only contain digit and/or numbers");
    }
}
