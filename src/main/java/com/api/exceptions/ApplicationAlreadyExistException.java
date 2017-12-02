package com.api.exceptions;

import com.api.entities.Application;

/**
 * extends RuntimeException
 * 
 * This exception will occur when candidate try to apply on same job twice.
 * candidate will consider unique with respect to email address
 * 
 * @author Rizwan Ishtiaq
 *
 */
public class ApplicationAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String jobTitle;
	private Application candidateApplication;

	public ApplicationAlreadyExistException(String jobTitle, Application candidateApplication) {
		this.jobTitle = jobTitle;
		this.candidateApplication = candidateApplication;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public Application getCandidateApplication() {
		return candidateApplication;
	}

}
