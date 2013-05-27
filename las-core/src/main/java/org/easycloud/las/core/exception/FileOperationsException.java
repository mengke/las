package org.easycloud.las.core.exception;

/**
 * File operations exception
 *
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class FileOperationsException extends RuntimeException {

	/**
	 * Constructor
	 * @param msg a user provided msg
	 */
	public FileOperationsException(String msg){
		super (msg);
	}

	/**
	 * Constructor
	 * @param t the throwable we are wrapping
	 */
	public FileOperationsException(Throwable t){
		super(t);
	}

	/**
	 * Constructor
	 * @param msg a user provided msg
	 * @param t the throwable we are wrapping
	 */
	public FileOperationsException(String msg, Throwable t){
		super (msg,t);
	}

	/**
	 * Unwrap to the root exception
	 */
	@Override
	public Throwable getCause(){
		Throwable t = super.getCause();
		Throwable c = t;
		while(t != null){
			c = t;
			t = t.getCause();
		}
		return c;
	}

}
