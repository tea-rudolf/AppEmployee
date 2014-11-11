package ca.ulaval.glo4003.appemployee.domain.exceptions;

public class PayPeriodAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 7767825991526775076L;

	public PayPeriodAlreadyExistsException() {
		super();
	}

	public PayPeriodAlreadyExistsException(String message) {
		super(message);
	}
}
