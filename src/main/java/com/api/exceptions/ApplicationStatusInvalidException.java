package com.api.exceptions;

/**
 * extends RuntimeException
 * 
 * Application specific (wrap-up) Exception. Can be use to throw, when try to
 * create ApplicationStatus Enum from string using valueOf() function
 * 
 * Constructor parameter msg will be equal to status string which failed to convert into
 * enum
 * 
 * @author Rizwan Ishtiaq
 *
 */
public class ApplicationStatusInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationStatusInvalidException(String msg) {
		super(msg);
	}

}
