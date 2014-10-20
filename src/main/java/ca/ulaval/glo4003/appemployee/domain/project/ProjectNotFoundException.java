package ca.ulaval.glo4003.appemployee.domain.project;

public class ProjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public ProjectNotFoundException() {
		super();
	}
	
	public ProjectNotFoundException(String message) {
		super(message);
	}
}
