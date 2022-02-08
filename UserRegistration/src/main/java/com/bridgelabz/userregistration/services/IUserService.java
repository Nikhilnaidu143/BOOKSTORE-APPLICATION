package com.bridgelabz.userregistration.services;

import java.util.List;

import com.bridgelabz.userregistration.dto.UserDTO;
import com.bridgelabz.userregistration.models.User;

public interface IUserService {

	/*** Declaring methods. ***/
	public String helloMessage(String token);

	public User addUser(UserDTO user);

	public User updateUserDetails(UserDTO user, String id, String token);

	public List<User> readAllUsersData(String token);

	public User readUserById(String id, String token);

	public String deleteUserById(String id, String token);

	public User userVerification(String token);

	public User userLogin(String email, String password);

	public User setNewPassword(String token, String newPassword);

	public String sendOtp(String token);

	public User verifyOtp(String token, int otp, int enter_otp);

	public String forgetPasswordLink(String email);

	public String sendEmailIfSubscriptionNearToExpiry();
}