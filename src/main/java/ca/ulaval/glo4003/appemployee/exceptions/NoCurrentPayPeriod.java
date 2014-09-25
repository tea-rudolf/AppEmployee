package ca.ulaval.glo4003.appemployee.exceptions;

public class NoCurrentPayPeriod extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoCurrentPayPeriod(String message) {
		super(message);
	}
	
}
