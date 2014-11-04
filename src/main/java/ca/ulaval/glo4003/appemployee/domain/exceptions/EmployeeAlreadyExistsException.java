package ca.ulaval.glo4003.appemployee.domain.exceptions;

public class EmployeeAlreadyExistsException extends RuntimeException {
    
    private static final long serialVersionUID = 7767825991526775076L;

    public EmployeeAlreadyExistsException() {
        super();
    }

    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }

}
