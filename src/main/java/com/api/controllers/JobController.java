package com.api.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.entities.Application;
import com.api.entities.Offer;
import com.api.exceptions.OfferAlreadyExistException;
import com.api.exceptions.OfferNotFoundException;
import com.api.services.ApplicationService;
import com.api.services.OfferService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/jobs")
public class JobController {

	private OfferService offerService;
	private ApplicationService applicationService;
	
	// ------------- Job -------------
	@RequestMapping(method = RequestMethod.GET)
	public List<Offer> listAllJobs() {
		return offerService.getAllOffers();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Offer> createJob(@Valid @RequestBody Offer offer, UriComponentsBuilder ucb)
			throws OfferAlreadyExistException {
		offer = offerService.createNewOffer(offer);

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/jobs/").path(offer.getJobTitle()).build().toUri();

		headers.setLocation(locationUri);
		ResponseEntity<Offer> responseEntity = new ResponseEntity<Offer>(offer, headers, HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(value = "/{jobTile}", method = RequestMethod.GET)
	public Offer offerByJobTitle(@PathVariable String jobTile) {
		return offerService.findOfferByJobTitle(jobTile).orElseThrow(() -> new OfferNotFoundException(jobTile));
	}

	// ------------- application -------------
	@RequestMapping(value = "/{jobTitle}/applications", method = RequestMethod.GET)
	public List<Application> applicationsOnOffer(@PathVariable String jobTitle) {
		return applicationService.getApplicationsOnOffer(jobTitle);
	}

	@RequestMapping(value = "/{jobTitle}/applications", method = RequestMethod.POST)
	public ResponseEntity<Application> applyApplications(@PathVariable String jobTitle,
			@Valid @RequestBody Application application, UriComponentsBuilder ucb) {
		application = applicationService.applyOnOffer(jobTitle, application);

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/jobs/").path(jobTitle).path("/applications/").path(application.getCandidateEmail())
				.build().toUri();

		headers.setLocation(locationUri);
		ResponseEntity<Application> responseEntity = new ResponseEntity<Application>(application, headers,
				HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(value = "/{jobTitle}/applications/{candidateEmail:.+}", method = RequestMethod.GET)
	public Application applicationOnOffer(@PathVariable String jobTitle, @PathVariable String candidateEmail) {
		return applicationService.getByOfferAndCandidateEmail(jobTitle, candidateEmail);
	}

	@RequestMapping(value = "/{jobTitle}/applications/{candidateEmail}/change/{status}", method = RequestMethod.PUT)
	public void applicationStatusChange(@PathVariable String jobTitle, @PathVariable String candidateEmail,
			@PathVariable String status) {
		applicationService.updateStatus(jobTitle, candidateEmail, status);
	}

	// ------------- Getters & Setters -------------
	@Autowired
	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	@Autowired
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

}
