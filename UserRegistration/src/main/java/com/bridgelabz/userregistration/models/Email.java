package com.bridgelabz.userregistration.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class Email {

	private String to;
	private String from;
	private String subject;
	private String body;

}