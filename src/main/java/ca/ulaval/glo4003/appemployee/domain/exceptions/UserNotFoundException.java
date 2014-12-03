package ca.ulaval.glo4003.appemployee.domain.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
