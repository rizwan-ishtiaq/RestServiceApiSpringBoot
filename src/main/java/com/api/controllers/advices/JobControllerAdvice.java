package com.api.controllers.advices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.exceptions.ApplicationAlreadyExistException;
import com.api.exceptions.ApplicationNotFoundException;
import com.api.exceptions.ApplicationStatusInvalidException;
import com.api.exceptions.OfferAlreadyExistException;
import com.api.exceptions.OfferNotFoundException;
import com.api.exceptions.ResponseError;

@RestControllerAdvice
public class JobControllerAdvice {

	// ------------- Exception Handling -------------
	@ExceptionHandler(OfferNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public List<ResponseError> offerNotFound(OfferNotFoundException e) {
		return Arrays.asList(new ResponseError(404, "Offer [" + e.getMessage() + "] not found"));
	}

	@ExceptionHandler(OfferAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public List<ResponseError> offerExist(OfferAlreadyExistException e) {
		return Arrays.asList(new ResponseError(409, "Job Already Exist With Title [" + e.getMessage() + "]"));
	}

	@ExceptionHandler(ApplicationNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public List<ResponseError> applicationNotFound(ApplicationNotFoundException e) {
		return Arrays.asList(new ResponseError(404, "Application [" + e.getMessage() + "] not found"));
	}

	@ExceptionHandler(ApplicationAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public List<ResponseError> applicationExist(ApplicationAlreadyExistException e) {
		return Arrays.asList(new ResponseError(409, "Candidate [" + e.getCandidateApplication().getCandidateEmail()
				+ "] already applied on Offer[" + e.getJobTitle() + "]"));
	}

	@ExceptionHandler(ApplicationStatusInvalidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<ResponseError> badApplicationStatus(ApplicationStatusInvalidException e) {
		return Arrays.asList(new ResponseError(400, "Application Status [" + e.getMessage() + "] is not valid"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<ResponseError> badRequest(MethodArgumentNotValidException e) {
		return e.getBindingResult().getFieldErrors().stream()
				.map(o -> new ResponseError(400, o.getField() + " " + o.getDefaultMessage()))
				.collect(Collectors.toList());
	}

}
