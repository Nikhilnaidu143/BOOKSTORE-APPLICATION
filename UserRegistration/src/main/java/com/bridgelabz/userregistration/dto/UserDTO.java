
package com.bridgelabz.userregistration.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import com.bridgelabz.userregistration.services.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

public @ToString class UserDTO {

	@NotNull(message = "First name cannot be null...!")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{2,}$", message = "First name validation failed..!")
	public String first_name;

	@NotNull(message = "Last name cannot be null...!")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{2,}$", message = "Last name validation failed..!")
	public String last_name;

//	@NotNull(message = "KYC cannot be null...!")
	public String kyc;

	@NotNull(message = "Date of birth cannot be null...!")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Date of birth cannot be future date...!")
	public LocalDate dob;

	@NotNull(message = "Registered date cannot be null...!")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Registered date cannot be future date...!")
	public LocalDate registered_date;

//	@NotNull(message = "Updated date cannot be null...!")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Updated date cannot be future date...!")
	public LocalDate updated_date;

	@NotNull(message = "Password cannot be null...!")
	@Pattern(regexp = UserService.PASSWORD_REGEX, message = "Password validation failed..!")
	public String password;

	@NotNull(message = "Email cannot be null...!")
	@Email(regexp = UserService.EMAIL_REGEX, message = "Email validation failed..!")
	public String email;
}