package ca.ulaval.glo4003.appemployee.domain.expense;

public class ExpenseAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 7767825991526775076L;

	public ExpenseAlreadyExistsException() {
		super();
	}

	public ExpenseAlreadyExistsException(String message) {
		super(message);
	}
}
