package ca.ulaval.glo4003.appemployee.persistence;

public class FileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7767825991526775076L;

	public FileNotFoundException() {
		super();
	}
	
	public FileNotFoundException(String message) {
		super(message);
	}
}
