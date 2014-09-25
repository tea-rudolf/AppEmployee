package ca.ulaval.glo4003.appemployee.domain.task;

public class TaskExistsException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public TaskExistsException() {
		super();
	}
	
	public TaskExistsException(String message) {
		super(message);
	}
}
