package pw.react.backend.reactbackend.exception;

public class UserExists extends RuntimeException {

    /**
	 *
	 */
	
    public UserExists() {
        this("");
    }
	public UserExists(String message) {
        super(message);
    }
}