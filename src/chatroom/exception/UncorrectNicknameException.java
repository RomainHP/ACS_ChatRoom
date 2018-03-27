package chatroom.exception;

public class UncorrectNicknameException extends Exception {
    public UncorrectNicknameException(){
        super("Your nickname can only contain digit and/or numbers");
    }
}
