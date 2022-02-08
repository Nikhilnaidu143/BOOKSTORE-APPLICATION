package com.bridgelabz.userregistration.services;

import com.bridgelabz.userregistration.models.Email;

public interface IMailService {

	/*** Declaring methods. ***/
	public void send(Email email);

	public String getLink(String token);

	public String getOtpLink(String token, int Otp);

	public String getLinkForResetPassword(String createToken);
}
