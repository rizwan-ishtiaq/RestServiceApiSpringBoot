package com.api.services;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.api.entities.Offer;
import com.api.exceptions.OfferAlreadyExistException;
import com.api.repositories.OfferRepository;
import com.api.services.OfferService;
import com.api.services.OfferServiceImpl;

public class OfferServiceTest {

	static private OfferService os;

	@BeforeClass
	static public void createResources() {
		os = new OfferServiceImpl();
	}

	@Before
	public void newRepository() {
		((OfferServiceImpl) os).setOfferRepo(new OfferRepository());
	}

	@Test
	public void createJobOfferTest() throws OfferAlreadyExistException {
		Offer of = new Offer();
		of.setJobTitle("job1");
		of.setNumberOfApplications(1);
		of.setStartDate(LocalDate.now());

		os.createNewOffer(of);
	}

	@Test(expected = OfferAlreadyExistException.class)
	public void offerUniqueTitleTest() throws OfferAlreadyExistException {
		Offer of = new Offer();
		of.setJobTitle("job1");
		of.setNumberOfApplications(1);
		of.setStartDate(LocalDate.now());

		os.createNewOffer(of);
		os.createNewOffer(of);
	}

	@Test
	public void getOfferTest() throws OfferAlreadyExistException {
		createJobOfferTest();
		assertTrue("There should be only one Offer in Repository", os.getAllOffers().size() == 1);
	}

	@Test
	public void findOfferTest() throws OfferAlreadyExistException {
		createJobOfferTest();
		Optional<Offer> offer = os.findOfferByJobTitle("job1");
		assertTrue("No offer found with job1", offer.isPresent());
	}

}
