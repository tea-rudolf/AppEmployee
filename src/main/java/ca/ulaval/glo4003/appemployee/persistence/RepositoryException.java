package ca.ulaval.glo4003.appemployee.persistence;

public class RepositoryException extends RuntimeException {

	private static final long serialVersionUID = -6576974283731470459L;

	public RepositoryException() {
		super();
	}
	
	public RepositoryException(String message) {
		super(message);
	}
}
