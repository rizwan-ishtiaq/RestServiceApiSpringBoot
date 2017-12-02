package com.api.exceptions;

/**
 * extends Exception
 * 
 * This exception will occur when try to create Offer(job) with same job title
 * which already exists in system
 * 
 * Constructor parameter msg parameter will be equal to Job Title
 * 
 * @author Rizwan Ishtiaq
 *
 */
public class OfferAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public OfferAlreadyExistException(String message) {
		super(message);
	}

}
