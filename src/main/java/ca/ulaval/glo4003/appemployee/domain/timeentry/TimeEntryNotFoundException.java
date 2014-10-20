package ca.ulaval.glo4003.appemployee.domain.timeentry;

public class TimeEntryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public TimeEntryNotFoundException() {
		super();
	}

	public TimeEntryNotFoundException(String message) {
		super(message);
	}
}
