package next.exception;

public class CannotDeleteException extends Exception {

	public CannotDeleteException() {
		super();
	}

	public CannotDeleteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CannotDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotDeleteException(String message) {
		super(message);
	}

	public CannotDeleteException(Throwable cause) {
		super(cause);
	}
	
}
