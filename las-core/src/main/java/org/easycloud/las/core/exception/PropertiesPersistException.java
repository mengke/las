package org.easycloud.las.core.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-22
 */
public class PropertiesPersistException extends RuntimeException {

	public PropertiesPersistException() {
	}

	public PropertiesPersistException(String message) {
		super(message);
	}

	public PropertiesPersistException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertiesPersistException(Throwable cause) {
		super(cause);
	}

	public PropertiesPersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
