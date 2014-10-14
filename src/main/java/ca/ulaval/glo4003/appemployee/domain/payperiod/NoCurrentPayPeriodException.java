package ca.ulaval.glo4003.appemployee.domain.payperiod;

public class NoCurrentPayPeriodException extends RuntimeException {

	private static final long serialVersionUID = 4431482906710824875L;

	public NoCurrentPayPeriodException() {
		super();
	}
	
	public NoCurrentPayPeriodException(String message) {
		super(message);
	}
	
}
