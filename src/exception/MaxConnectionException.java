package exception;

public class MaxConnectionException extends Exception {

	public MaxConnectionException() {
		super("Max connection number reached");
	}
}
