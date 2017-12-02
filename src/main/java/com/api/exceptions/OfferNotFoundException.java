package com.api.exceptions;

/**
 * extends RuntimeException
 * 
 * Can be use to throw, when try to find Offer(job) with job title
 * 
 * Constructor parameter titleJob will be equal to title of job
 * 
 * @author Rizwan Ishtiaq
 *
 */
public class OfferNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OfferNotFoundException(String titleJob) {
		super(titleJob);
	}

}
