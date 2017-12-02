package com.api.services;

import java.util.List;
import java.util.Optional;

import com.api.entities.Application;
import com.api.entities.Offer;
import com.api.enums.ApplicationStatus;
import com.api.exceptions.ApplicationAlreadyExistException;
import com.api.exceptions.ApplicationNotFoundException;
import com.api.exceptions.ApplicationStatusInvalidException;
import com.api.exceptions.OfferNotFoundException;

/**
 * Service to perform operation related to Job(Offer)
 * 
 * @author Rizwan Ishtiaq
 *
 */
public interface ApplicationService {

	/**
	 * Apply new application on Offer. Candidate will not allowed to apply on same
	 * job more than one. The Default status of new application will be APPLIED.
	 * When new application is submitted successfully, this method will increase the
	 * number of applied applications in Offer
	 * 
	 * @param jobTitle
	 *            Title of job
	 * @param candidateApplication
	 *            {@link Application} object to apply on job
	 * @return Application object after persisting into repository
	 * @throws ApplicationAlreadyExistException
	 *             when candidate try to apply twice on same job
	 * 
	 * @throws OfferNotFoundException
	 *             if try to apply on a offer which not exists in repository
	 * 
	 * @see ApplicationStatus
	 * @see Offer
	 */
	Application applyOnOffer(String jobTitle, Application candidateApplication);

	/**
	 * Return the application of candidate on particular offer by candidate's email
	 * address.
	 * 
	 * @param jobTitle
	 *            Title of job
	 * @param candidateEmail
	 *            email address of candidate
	 * @return Optional<Application>
	 * 
	 * @throws OfferNotFoundException
	 *             If no offer exists on which user is trying to find candidate
	 *             application
	 */
	Optional<Application> findByOfferAndCandidateEmail(String jobTitle, String candidateEmail);

	/**
	 * Same like findByOfferAndCandidateEmail() method with different return type
	 * 
	 * @param jobTitle
	 *            Title of job
	 * @param candidateEmail
	 *            email address of candidate
	 * @return {@link Application} object after persisting into repository
	 * @throws OfferNotFoundException
	 *             If no offer exists on which user is trying to find candidate
	 *             application
	 * @throws ApplicationNotFoundException
	 *             If application not found on particular job
	 */
	Application getByOfferAndCandidateEmail(String jobTitle, String candidateEmail);

	/**
	 * Return all the applications applied on particular offer
	 * 
	 * @param jobTitle
	 *            Job of Title
	 * @return
	 * @throws OfferNotFoundException
	 *             If no offer exists on which trying to find all the applications
	 */
	List<Application> getApplicationsOnOffer(String jobTitle);

	/**
	 * Calculate the Count of all applications on all the offers
	 * 
	 * @return int
	 */
	int getTotalCount();

	/**
	 * Update the status of particular job on particular offer. It also triggers the
	 * notification event of status change on job
	 * 
	 * @param jobTitle
	 *            job title
	 * @param candidateEmail
	 *            email which is used to apply on job
	 * @param newStatus
	 *            new status of application
	 * 
	 * @throws ApplicationStatusInvalidException
	 *             If param newStatus can't convert to {@link ApplicationStatus}
	 * @throws OfferNotFoundException
	 *             If no offer exists on which user is trying change status of
	 *             application
	 * @throws ApplicationNotFoundException
	 *             If application not found of which trying to change status
	 * 
	 * @see NotificationService
	 */
	void updateStatus(String jobTitle, String candidateEmail, String newStatus);

}
