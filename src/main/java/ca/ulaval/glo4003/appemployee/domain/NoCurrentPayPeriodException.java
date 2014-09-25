package ca.ulaval.glo4003.appemployee.domain;


public class NoCurrentPayPeriodException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NoCurrentPayPeriodException(String message) {
		super(message);
	}
	
}
