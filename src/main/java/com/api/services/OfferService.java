package com.api.services;

import java.util.List;
import java.util.Optional;

import com.api.entities.Offer;
import com.api.exceptions.OfferAlreadyExistException;

/**
 * Service use to perform operation on Offer.
 * 
 * @author Rizwan Ishtiaq
 *
 */
public interface OfferService {

	/**
	 * Return all the Offers in Repository
	 * 
	 * @return List<Offer>
	 */
	List<Offer> getAllOffers();

	/**
	 * Find Offer in repository using jobTitle passed as a parameter by using equal
	 * compare
	 * 
	 * @param jobTile
	 *            Title of Job in Offer
	 * @return Optional<Offer>
	 */
	Optional<Offer> findOfferByJobTitle(String jobTile);

	/**
	 * Same like findOfferByJobTitle() but return type is different. Return actual
	 * Offer object or Runtime Exception
	 * 
	 * @param jobTile
	 * @return Offer
	 * @Throws OfferNotFoundException message parameter contain the title of job
	 */
	Offer getOfferByJobTitle(String jobTile);

	/**
	 * Create new Job(Offer). If Offer already exists in repository with job title
	 * then throw checked exception. Before persisting check startDate of Offer. If
	 * null put current date
	 * 
	 * @param offer
	 * @return Offer after persisting
	 * @throws OfferAlreadyExistException
	 *             message parameter contain the title of job
	 */
	Offer createNewOffer(Offer offer) throws OfferAlreadyExistException;

}
