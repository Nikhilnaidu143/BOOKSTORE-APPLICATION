package com.bridgelabz.userregistration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class ResponseDTO {

	private String message;
	private Object data;
	private String token;

	/*** Parameterized constructor. ***/
	public ResponseDTO(String message, Object data) {
		this.message = message;
		this.data = data;
	}

}