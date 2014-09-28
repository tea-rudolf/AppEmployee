package ca.ulaval.glo4003.appemployee.domain.user;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
