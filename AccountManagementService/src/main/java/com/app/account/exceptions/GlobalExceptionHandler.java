package com.app.account.exceptions;

import java.util.NoSuchElementException;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpClientErrorException.NotFound.class)
	public ResponseEntity<String> notFound(HttpClientErrorException.NotFound ex) {
		return new ResponseEntity<>("Requested account does not exist, please check your parameters.",
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<String> custIdRequired(InvalidDataAccessApiUsageException ex) {
		return new ResponseEntity<>("Account number is required", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> npe(NullPointerException ex) {
		return new ResponseEntity<>("Please make sure that you are passing all the fields", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> dataTypeCheckInAddMoney(HttpMessageNotReadableException ex) {
		return new ResponseEntity<>("Please enter correct datatypes", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noSuchElement(NoSuchElementException ex) {
		return new ResponseEntity<>("Requested account does not exist, please check your parameters",
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> globalExceptionHandler(Exception ex) {
		return new ResponseEntity<>("Exception occurred!", HttpStatus.BAD_REQUEST);
	}

}
