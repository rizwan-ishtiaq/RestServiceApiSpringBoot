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

import com.api.entities.Offer;
import com.api.exceptions.OfferAlreadyExistException;
import com.api.exceptions.OfferNotFoundException;
import com.api.services.OfferService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/jobs")
public class JobController {

	private OfferService offerService;
	
	
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


	// ------------- Getters & Setters -------------
	@Autowired
	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

}
