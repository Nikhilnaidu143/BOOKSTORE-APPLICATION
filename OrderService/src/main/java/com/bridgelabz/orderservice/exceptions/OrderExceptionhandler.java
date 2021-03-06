package com.bridgelabz.orderservice.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.bridgelabz.orderservice.dto.ResponseDTO;
import com.bridgelabz.orderservice.exceptions.customexception.OrderException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class OrderExceptionhandler {

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

	/*** Handling invalid date format exception. ***/
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		log.error("Invalid date format :- ", exception.getMessage());
		ResponseDTO responseDTO = new ResponseDTO(MESSAGE, "Date should be in (yyyy-MM-dd) this format...!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/*** Handling custom exceptions. ***/
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ResponseDTO> handleOrderException(OrderException exception) {
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
