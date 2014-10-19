package ca.ulaval.glo4003.appemployee.domain.task;

public class TaskNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public TaskNotFoundException() {
		super();
	}

	public TaskNotFoundException(String message) {
		super(message);
	}
}
