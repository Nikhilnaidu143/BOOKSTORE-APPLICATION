package com.bridgelabz.bookstore.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.exceptions.customexceptions.BookException;

@ControllerAdvice
public class BookExceptionHandler {

	/*** Constant error message. ***/
	private static final String MESSAGE = "Exception while procession REST request.";

	/*** Handling validation failed exceptions. ***/
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
		List<String> errorMssg = errorList.stream().map(objErr -> objErr.getDefaultMessage())
				.collect(Collectors.toList());
		ResponseDTO responseDTO = new ResponseDTO(MESSAGE, errorMssg);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/*** Handling custom exceptions. ***/
	@ExceptionHandler(BookException.class)
	public ResponseEntity<ResponseDTO> handleBookException(BookException exception) {
		ResponseDTO responseDTO = new ResponseDTO(MESSAGE, exception.getMessage());
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/*** Handling invalid token exception. ***/
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ResponseDTO> handleHttpClientErrorException(HttpClientErrorException exception) {
		ResponseDTO responseDTO = new ResponseDTO(MESSAGE, exception.getMessage());
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.UNAUTHORIZED);
	}
}