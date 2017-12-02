package com.api.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.api.enums.ApplicationStatus;

/**
 * POJO class with private members, setter and getters
 * 
 * @author Rizwan Ishtiaq
 */
public class Application {

	// related offer
	private Offer relatedOffer;
	// (unique per Offer)
	@NotNull
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email address is not valid")
	private String candidateEmail;
	private String resumeText;
	private ApplicationStatus status;

	public Offer getRelatedOffer() {
		return relatedOffer;
	}

	public void setRelatedOffer(Offer appliedOn) {
		this.relatedOffer = appliedOn;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

}
