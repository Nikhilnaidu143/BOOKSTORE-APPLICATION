package com.bridgelabz.userregistration.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.userregistration.dto.ResponseDTO;
import com.bridgelabz.userregistration.dto.UserDTO;
import com.bridgelabz.userregistration.models.Email;
import com.bridgelabz.userregistration.models.User;
import com.bridgelabz.userregistration.services.IMailService;
import com.bridgelabz.userregistration.services.IUserService;
import com.bridgelabz.userregistration.utility.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired // Autowired annotation is used for automatic dependency injection.
	private IUserService userService;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private IMailService mailService;

	/*** Simple hello message for checking. ***/
	@GetMapping(value = { "", "/", "/home" })
	public ResponseEntity<ResponseDTO> sayHello(@RequestHeader(name = "token") String token) {
		String mssg = userService.helloMessage(token);
		ResponseDTO responseDTO = new ResponseDTO("Get Call successfull.", mssg, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Adding user deatils in the database. ***/
	@PostMapping(value = "/add")
	public ResponseEntity<ResponseDTO> add(@Valid @RequestBody UserDTO user) {
		log.info("User DTO :- " + user.toString()); // logging.
		User userData = userService.addUser(user);
		String token = tokenUtil.createToken(userData.getId());
		Email email = new Email(userData.getEmail(), "nnikhil976@gmail.com", "Verification Mail", "Hello "
				+ userData.getFirst_name() + " " + userData.getLast_name() + " ====> " + mailService.getLink(token));
		mailService.send(email);
		ResponseDTO responseDTO = new ResponseDTO("Post Call for user successfull..!", userData, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Updating already existing user details. ***/
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDTO> update(@Valid @RequestBody UserDTO user, @PathVariable String id,
			@RequestHeader(name = "token") String token) {
		User userData = userService.updateUserDetails(user, id, token);
		ResponseDTO responseDTO = new ResponseDTO("Put Call for user successfull..!", userData,
				tokenUtil.createToken(Long.parseLong(id)));
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Read All user details . ***/
	@GetMapping(value = "/readAll")
	public ResponseEntity<ResponseDTO> readAll(@RequestHeader(name = "token") String token) {
		List<User> usersData = userService.readAllUsersData(token);
		ResponseDTO responseDTO = new ResponseDTO("Read all users data Call successfull.", usersData, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** read user details by using ID . ***/
	@GetMapping(value = "/read/{id}")
	public ResponseEntity<ResponseDTO> getEmployee(@PathVariable String id,
			@RequestHeader(name = "token") String token) {
		User userData = userService.readUserById(id, token);
		ResponseDTO responseDTO = new ResponseDTO("Read Call for ID successfull..!", userData,
				tokenUtil.createToken(Long.parseLong(id)));
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Delete user details by using ID. ***/
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable String id, @RequestHeader(name = "token") String token) {
		String bodyMessage = userService.deleteUserById(id, token);
		ResponseDTO responseDTO = new ResponseDTO("Delete Call for ID successfull..!", bodyMessage,
				tokenUtil.createToken(Long.parseLong(id)));
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** User Verification ***/
	@GetMapping(value = "/verify/{token}")
	public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token) {
		User userData = userService.userVerification(token);
		ResponseDTO responseDTO = new ResponseDTO("Verification for user successfull..!", userData, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** User login ***/
	@GetMapping(value = "/login/{email}/{password}")
	public ResponseEntity<ResponseDTO> login(@PathVariable String email, @PathVariable String password) {
		User userData = userService.userLogin(email, password);
		ResponseDTO responseDTO = new ResponseDTO("Login successfull..!", userData,
				tokenUtil.createToken(userData.getId()));
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Forget Password. ***/
	@GetMapping(value = "/forgetPassword")
	public ResponseEntity<ResponseDTO> forgetPassword(@RequestParam(value = "email") String email) {
		String getMessage = userService.forgetPasswordLink(email);
		ResponseDTO responseDTO = new ResponseDTO("Rest PassWord sent to email successfully..!", getMessage);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** changing password. ***/
	@GetMapping(value = "/resetPassword/{token}/{newPassword}")
	public ResponseEntity<ResponseDTO> newPassword(@PathVariable String token,
			@PathVariable String newPassword) {
		User userData = userService.setNewPassword(token, newPassword);
		ResponseDTO responseDTO = new ResponseDTO("PassWord has been changed successfully..!", userData, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Sending otp. ***/
	@GetMapping(value = "/sendOTP")
	public ResponseEntity<ResponseDTO> sendOTP(@RequestHeader(name = "token") String token) {
		String getMessage = userService.sendOtp(token);
		ResponseDTO responseDTO = new ResponseDTO("OTP send successfully..!", getMessage, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Verifying otp. ***/
	@GetMapping(value = "/verifyOTP/{token}/{otp}")
	public ResponseEntity<ResponseDTO> verifyOTP(@PathVariable String token, @PathVariable int otp,
			@RequestParam(value = "enter_otp") int enter_otp) {
		User userData = userService.verifyOtp(token, otp, enter_otp);
		ResponseDTO responseDTO = new ResponseDTO("OTP verified successfully..!", userData, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Sending warning to email if Expiry date is near. ***/
	@GetMapping(value = "/expiry")
	public ResponseEntity<ResponseDTO> expiryEmail() {
		String getMessage = userService.sendEmailIfSubscriptionNearToExpiry();
		ResponseDTO responseDTO = new ResponseDTO("Expiry Warning sent...!", getMessage);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Purchasing suscription for specific user. ***/
	@PutMapping(value = "/purchase")
	public ResponseEntity<ResponseDTO> purchase(@RequestHeader(name = "token") String token) {
		User userData = userService.purchaseSubscription(token);
		ResponseDTO responseDTO = new ResponseDTO("Purchased Successfully...!", userData, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
}