package ca.ulaval.glo4003.appemployee.domain.exceptions;

public class ExpenseNotFoundException extends Exception {

	private static final long serialVersionUID = 7767825991526775076L;

	public ExpenseNotFoundException(String message) {
		super(message);
	}

}
