package com.api.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.entities.Application;
import com.api.services.ApplicationService;

@RestController
@RequestMapping("/jobs")
public class JobApplicationController {

	private ApplicationService applicationService;

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
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

}
