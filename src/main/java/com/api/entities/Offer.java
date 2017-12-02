package com.api.entities;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * POJO class with private members, setter and getters
 * 
 * @author Rizwan Ishtiaq
 */
public class Offer {

	// (unique)
	@NotNull
	@Size(min = 2, max = 100)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Ony letters and numbers allowed")
	private String jobTitle;
	private LocalDate startDate;
	private int numberOfApplications;

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public int getNumberOfApplications() {
		return numberOfApplications;
	}

	public void setNumberOfApplications(int numberOfApplications) {
		this.numberOfApplications = numberOfApplications;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
			return false;
		return true;
	}

}
