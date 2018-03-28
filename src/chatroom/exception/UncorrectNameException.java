package chatroom.exception;

public class UncorrectNameException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1144251884488026645L;

	public UncorrectNameException(String name){
        super(name + " can only contain digit and/or numbers");
    }
	
	public UncorrectNameException(){
        super("Name can only contain digit and/or numbers");
    }
}
