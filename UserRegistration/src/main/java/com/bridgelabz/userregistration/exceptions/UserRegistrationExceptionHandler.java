package com.bridgelabz.userregistration.exceptions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.bridgelabz.userregistration.dto.ResponseDTO;
import com.bridgelabz.userregistration.exceptions.customexception.UserException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class UserRegistrationExceptionHandler {

	private static final String ERROR_MESSAGE = "Exception while procession REST request.";

	/*** Handling Validation failed exception. ***/
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
		List<String> errorMssg = errorList.stream().map(objErr -> objErr.getDefaultMessage())
				.collect(Collectors.toList());
		ResponseDTO responseDTO = new ResponseDTO(ERROR_MESSAGE, errorMssg);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/*** Handling incorrect date format exception. ***/
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		log.error("Invalid date format :- ", exception.getMessage());
		ResponseDTO responseDTO = new ResponseDTO(ERROR_MESSAGE, "Date should be in (yyyy-MM-dd) this format...!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/*** Handling id not found exception. ***/
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ResponseDTO> handleUserException(UserException exception) {
		ResponseDTO responseDTO = new ResponseDTO(ERROR_MESSAGE, exception.getMessage());
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/*** Handling exception when token is not valid. ***/
	@ExceptionHandler(SignatureVerificationException.class)
	public ResponseEntity<ResponseDTO> handleSignatureVerificationException(SignatureVerificationException exception) {
		ResponseDTO responseDTO = new ResponseDTO(ERROR_MESSAGE, exception.getMessage());
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.UNAUTHORIZED);
	}

	/*** Handling exception if token is not present. ***/
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ResponseDTO> handleNoSuchElementException(NoSuchElementException exception) {
		ResponseDTO responseDTO = new ResponseDTO(ERROR_MESSAGE, exception.getMessage());
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

}