package common.exception;

public class FrameworkException extends Exception {

	private static final long serialVersionUID = 1L;

	public FrameworkException() {
	}

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrameworkException(Throwable cause) {
		super(cause);
	}

}
