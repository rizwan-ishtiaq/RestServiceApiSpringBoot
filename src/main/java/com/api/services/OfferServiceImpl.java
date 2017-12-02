package com.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Offer;
import com.api.exceptions.OfferAlreadyExistException;
import com.api.exceptions.OfferNotFoundException;
import com.api.repositories.OfferRepository;

/**
 * Default implementation of {@link OfferService}. This will be injected by
 * spring boot by default
 * 
 * @author Rizwan Ishtiaq
 *
 */
@Service
public class OfferServiceImpl implements OfferService {

	private OfferRepository offerRepo;

	@Override
	public List<Offer> getAllOffers() {
		// Get all the offers from repository
		return offerRepo.getOffers();
	}

	@Override
	public Optional<Offer> findOfferByJobTitle(final String jobTitle) {
		return offerRepo.getOffers().stream().filter(o -> o.getJobTitle().equalsIgnoreCase(jobTitle)).findFirst();
	}

	@Override
	public Offer getOfferByJobTitle(String jobTitle) {
		return findOfferByJobTitle(jobTitle).orElseThrow(() -> new OfferNotFoundException(jobTitle));
	}

	@Override
	public Offer createNewOffer(Offer offer) throws OfferAlreadyExistException {
		// Finding Offer to check if it exist before
		Optional<Offer> dbOffer = findOfferByJobTitle(offer.getJobTitle());
		// Duplicate Job w.r.t job title is not allowed to create
		if (dbOffer.isPresent())
			throw new OfferAlreadyExistException(offer.getJobTitle());
		// Offer job by default by current date as start date if not present while
		// creating job
		if (offer.getStartDate() == null)
			offer.setStartDate(LocalDate.now());
		offerRepo.getOffers().add(offer);
		return offer;
	}

	// ------------- Getter & Setters -------------
	@Autowired
	public void setOfferRepo(OfferRepository offerRepo) {
		this.offerRepo = offerRepo;
	}

}
