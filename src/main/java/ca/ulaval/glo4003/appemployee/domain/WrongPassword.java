package ca.ulaval.glo4003.appemployee.domain;

public class WrongPassword extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongPassword(String message) {
		super(message);
	}

}
