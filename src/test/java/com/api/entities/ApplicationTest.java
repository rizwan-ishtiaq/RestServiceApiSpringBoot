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

import com.api.entities.Application;

public class ApplicationTest {

	private static Validator validator;
	private Application application;

	@BeforeClass
	public static void setupTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Before
	public void eachSetup() {
		application = new Application();
	}

	@Test
	public void invalidEmailShouldFailValidation() {
		application.setCandidateEmail("rizwan");
		Set<ConstraintViolation<Application>> violations = validator.validate(application);
		assertFalse(violations.isEmpty());
	}

	@Test
	public void nullEmailShouldFailValidation() {
		application.setCandidateEmail(null);
		Set<ConstraintViolation<Application>> violations = validator.validate(application);
		assertFalse(violations.isEmpty());
	}

}
