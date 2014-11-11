package ca.ulaval.glo4003.appemployee.domain.exceptions;

public class PayPeriodNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public PayPeriodNotFoundException() {
		super();
	}

	public PayPeriodNotFoundException(String message) {
		super(message);
	}
}
