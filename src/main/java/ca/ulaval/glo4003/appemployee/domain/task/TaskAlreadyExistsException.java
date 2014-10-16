package ca.ulaval.glo4003.appemployee.domain.task;

public class TaskAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public TaskAlreadyExistsException() {
		super();
	}
	
	public TaskAlreadyExistsException(String message) {
		super(message);
	}
}
