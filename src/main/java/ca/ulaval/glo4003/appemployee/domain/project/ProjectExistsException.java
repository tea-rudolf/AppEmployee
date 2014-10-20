package ca.ulaval.glo4003.appemployee.domain.project;

public class ProjectExistsException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public ProjectExistsException() {
		super();
	}

	public ProjectExistsException(String message) {
		super(message);
	}
}
