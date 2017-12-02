package com.api.exceptions;

/**
 * extends RuntimeException
 * 
 * Can be use to throw, when try to find application on particular Offer(Job)
 * with candidate's email address
 * 
 * Constructor parameter msg will be equal to candidate email address
 * 
 * @author Rizwan Ishtiaq
 *
 */
public class ApplicationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationNotFoundException(String msg) {
		super(msg);
	}

}
