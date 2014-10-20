package ca.ulaval.glo4003.appemployee.domain.payperiod;

public class CurrentDateIsInvalidException extends RuntimeException {

	private static final long serialVersionUID = 4431482906710824875L;

	public CurrentDateIsInvalidException() {
		super();
	}
	
	public CurrentDateIsInvalidException(String message) {
		super(message);
	}
	
}
