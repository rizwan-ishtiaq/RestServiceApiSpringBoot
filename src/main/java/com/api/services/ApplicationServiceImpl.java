package com.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Application;
import com.api.entities.Offer;
import com.api.enums.ApplicationStatus;
import com.api.exceptions.ApplicationAlreadyExistException;
import com.api.exceptions.ApplicationNotFoundException;
import com.api.exceptions.ApplicationStatusInvalidException;
import com.api.repositories.ApplicationRepository;

/**
 * Default implementation of {@link ApplicationService}. This will be injected
 * by spring boot by default
 * 
 * @author Rizwan Ishtiaq
 *
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

	private ApplicationRepository applicationRepository;
	private OfferService offerService;
	private NotificationService notificationService;

	@Override
	public Application applyOnOffer(String jobTitle, Application candidateApplication) {
		// Check candidate already applied on job
		if (findByOfferAndCandidateEmail(jobTitle, candidateApplication.getCandidateEmail()).isPresent()) {
			throw new ApplicationAlreadyExistException(jobTitle, candidateApplication);
		}
		Offer offer = offerService.getOfferByJobTitle(jobTitle);

		// As Repository is backed by array and methods are returning reference to
		// actual objects no need to do any thing else to persist changes
		// We can also define a method in OfferService to increment in application count
		offer.setNumberOfApplications(offer.getNumberOfApplications() + 1);

		candidateApplication.setRelatedOffer(offer);
		// Default status will be Applied
		candidateApplication.setStatus(ApplicationStatus.APPLIED);

		applicationRepository.getApplications().add(candidateApplication);
		return candidateApplication;
	}

	@Override
	public Optional<Application> findByOfferAndCandidateEmail(String jobTitle, String candidateEmail) {
		Offer offer = offerService.getOfferByJobTitle(jobTitle);
		return applicationRepository.findByCandidateEmail(candidateEmail).stream()
				.filter(a -> a.getRelatedOffer().equals(offer)).findFirst();
	}

	@Override
	public Application getByOfferAndCandidateEmail(String jobTitle, String candidateEmail) {
		return findByOfferAndCandidateEmail(jobTitle, candidateEmail)
				.orElseThrow(() -> new ApplicationNotFoundException(candidateEmail));
	}

	@Override
	public List<Application> getApplicationsOnOffer(String jobTitle) {
		Offer offer = offerService.getOfferByJobTitle(jobTitle);
		return applicationRepository.getApplications().stream().filter(a -> a.getRelatedOffer().equals(offer))
				.collect(Collectors.toList());
	}

	@Override
	public int getTotalCount() {
		return applicationRepository.getApplications().size();
	}

	@Override
	public Application updateStatus(String jobTitle, String candidateEmail, String newStatus) {
		ApplicationStatus newStat;
		// Convert string status to Enum
		try {
			newStat = ApplicationStatus.valueOf(newStatus.toUpperCase());
		} catch (RuntimeException e) {
			throw new ApplicationStatusInvalidException(newStatus);
		}
		Application application = getByOfferAndCandidateEmail(jobTitle, candidateEmail);
		ApplicationStatus oldStatus = application.getStatus();
		// No need to persist, because working in memory
		application.setStatus(newStat);
		// Status change successfully now notify
		notificationService.notifyApplicationStatusChange(oldStatus, newStat);
		return application;
	}

	// ------------- Getter & Setters -------------
	@Autowired
	public void setApplicationRepository(ApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}

	@Autowired
	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	@Autowired
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

}
