package com.bridgelabz.userregistration.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.userregistration.dto.UserDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "user_details")
public @Data class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String first_name;
	
	private String last_name;
	
	private String kyc;
	
	private LocalDate dob;
	
	private LocalDate registered_date;
	
	private LocalDate updated_date;
	
	private String password;
	
	private String email;
	
	private boolean verify;
	
	private int otp;
	
	private LocalDate purchase_date;
	
	private LocalDate expiry_date;
	
	/*** Parameterized Constructor. ***/
	public User(UserDTO userDTO) {
		this.first_name = userDTO.first_name;
		this.last_name = userDTO.last_name;
		this.kyc = userDTO.kyc;
		this.dob = userDTO.dob;
		this.registered_date = userDTO.registered_date;
		this.updated_date = userDTO.updated_date;
		this.password = userDTO.password;
		this.email = userDTO.email;
		this.verify = false;
		this.otp = 0;
		this.purchase_date = null;
		this.expiry_date = null;
	}
	
	/*** Parameterized Constructor.(Constructor Overloading.) ***/
	public User(long id , UserDTO userDTO) {
		this.id = id;
		this.first_name = userDTO.first_name;
		this.last_name = userDTO.last_name;
		this.kyc = userDTO.kyc;
		this.dob = userDTO.dob;
		this.registered_date = userDTO.registered_date;
		this.updated_date = userDTO.updated_date;
		this.password = userDTO.password;
		this.email = userDTO.email;
		this.verify = false;
		this.otp = 0;
		this.purchase_date = null;
		this.expiry_date = null;
	}
}