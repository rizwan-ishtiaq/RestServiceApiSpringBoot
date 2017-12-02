package com.api.entities;

import static org.junit.Assert.assertFalse;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.api.entities.Offer;

public class OfferTest {

	private static Validator validator;
	private Offer offer;

	@BeforeClass
	public static void setupTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Before
	public void eachSetup() {
		offer = new Offer();
	}

	@Test
	public void invalidJobTitleShouldFailValidation() {
		offer.setJobTitle("abc-def");
		Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
		assertFalse(violations.isEmpty());
	}

	@Test
	public void nullJobTitleShouldFailValidation() {
		offer.setJobTitle(null);
		Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
		assertFalse(violations.isEmpty());
	}

	@Test
	public void minLenghtJobTitleShouldFailValidation() {
		offer.setJobTitle("1");
		Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
		assertFalse(violations.isEmpty());
	}
}
