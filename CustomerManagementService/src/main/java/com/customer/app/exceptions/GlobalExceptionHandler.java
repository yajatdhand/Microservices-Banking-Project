package com.customer.app.exceptions;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noSuchElementException(NoSuchElementException ex) {
		return new ResponseEntity<>("No such customer exists, please check your parameters.", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> incorrectDataTypeException(HttpMessageNotReadableException ex) {
		return new ResponseEntity<>("Please enter correct data types for input", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> incorrectDataTypeException(MethodArgumentTypeMismatchException ex) {
		return new ResponseEntity<>("Please enter correct data types for input", HttpStatus.BAD_REQUEST);
	} // passing too many numbers/characters in getCustomer

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<String> incorrectDataTypeException(InvalidDataAccessApiUsageException ex) {
		return new ResponseEntity<>("Customer ID is required", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> nonNullValuesInAddRequest(DataIntegrityViolationException ex) {
		return new ResponseEntity<>("Please enter all customer details", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> npe(NullPointerException ex) {
		return new ResponseEntity<>("Request body is empty!", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> globalExceptionHandler(Exception ex) {
		return new ResponseEntity<>("Exception occurred!".concat(Arrays.toString(ex.getStackTrace())),
				HttpStatus.BAD_REQUEST);
	}
}
