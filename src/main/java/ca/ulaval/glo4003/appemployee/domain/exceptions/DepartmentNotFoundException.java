package ca.ulaval.glo4003.appemployee.domain.exceptions;

public class DepartmentNotFoundException extends Exception {

	private static final long serialVersionUID = 7767825991526775076L;

	public DepartmentNotFoundException() {
		super();
	}

	public DepartmentNotFoundException(String message) {
		super(message);
	}

}
