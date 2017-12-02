package com.api.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.api.entities.Application;
import com.api.entities.Offer;
import com.api.exceptions.ApplicationAlreadyExistException;
import com.api.exceptions.ApplicationStatusInvalidException;
import com.api.exceptions.OfferAlreadyExistException;
import com.api.repositories.ApplicationRepository;
import com.api.repositories.OfferRepository;
import com.api.services.ApplicationService;
import com.api.services.ApplicationServiceImpl;
import com.api.services.OfferService;
import com.api.services.OfferServiceImpl;

public class ApplicationServiceTest {

	static private OfferService os;
	static private ApplicationService as;

	private Application application;

	@BeforeClass
	static public void createResources() {
		os = new OfferServiceImpl();
		as = new ApplicationServiceImpl();
		((ApplicationServiceImpl) as).setOfferService(os);
	}

	/**
	 * For each test re-set the repository and add required data
	 */
	@Before
	public void eachTest() {
		Offer of = new Offer();
		of.setJobTitle("job1");
		of.setNumberOfApplications(0);
		of.setStartDate(LocalDate.now());
		OfferRepository offerRepository = new OfferRepository();
		offerRepository.getOffers().add(of);

		of = new Offer();
		of.setJobTitle("job2");
		offerRepository.getOffers().add(of);

		((OfferServiceImpl) os).setOfferRepo(offerRepository);
		((ApplicationServiceImpl) as).setApplicationRepository(new ApplicationRepository());

		application = new Application();
		application.setCandidateEmail("rizwan@test.com");
		application.setResumeText("Rizwan resume");
		application.setRelatedOffer(of);
	}

	@Test
	public void applyApplicationShouldPass() {
		as.applyOnOffer("job1", application);
	}

	@Test(expected = ApplicationAlreadyExistException.class)
	public void uniqueApplicationPerOfferTest() throws OfferAlreadyExistException {
		as.applyOnOffer("job1", application);
		as.applyOnOffer("job1", application);
	}

	@Test
	public void getApplicationsOnOfferTest() {
		ApplicationRepository appRapo = new ApplicationRepository();
		appRapo.getApplications().add(application);
		((ApplicationServiceImpl) as).setApplicationRepository(appRapo);

		assertEquals("There should be only one application on Job1", 1, as.getApplicationsOnOffer("job2").size());
	}

	@Test
	public void findApplicationTest() {
		ApplicationRepository appRapo = new ApplicationRepository();
		appRapo.getApplications().add(application);
		((ApplicationServiceImpl) as).setApplicationRepository(appRapo);

		Optional<Application> app = as.findByOfferAndCandidateEmail("job2", "rizwan@test.com");
		assertTrue("No Application found with job1", app.isPresent());
	}

	@Test(expected = ApplicationStatusInvalidException.class)
	public void updateWrongStatusShouldFaild() {
		ApplicationRepository appRapo = new ApplicationRepository();
		appRapo.getApplications().add(application);
		((ApplicationServiceImpl) as).setApplicationRepository(appRapo);

		as.updateStatus("job2", "rizwan@test.com", "wrong");
	}

}
