package ca.ulaval.glo4003.appemployee.persistence;

public class MarshallingException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public MarshallingException() {
		super();
	}
	
	public MarshallingException(String message) {
		super(message);
	}
	
	public MarshallingException(String message, Exception e) {
		super(message, e);
	}
}
