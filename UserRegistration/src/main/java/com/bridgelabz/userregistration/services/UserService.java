package com.bridgelabz.userregistration.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.userregistration.dto.UserDTO;
import com.bridgelabz.userregistration.exceptions.customexception.UserException;
import com.bridgelabz.userregistration.models.User;
import com.bridgelabz.userregistration.repository.UserRepository;
import com.bridgelabz.userregistration.utility.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements IUserService {

	/*** Constant custom exception variables. ***/
	private static final String ID_NOT_FOUND = "OOPS! ID not found in the database...!";
	private static final String TOKEN_INVALID = "OOPS! Token is invalid....!";
	private static final String NON_VERIFIED_USER = "OOPS! not verified user...!";
	private static final String USER_NOT_FOUND = "OOPS! User is not there in database.";
	private static final String PASSWORD_NOTVALID = "Password Validation failed..!";

	/*** Constant password and email Regex patterns. ****/
	private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=[\\w]*[\\W][\\w]*$)(?=.*[a-z]).{8,}$";
	private static final String EMAIL_REGEX = "^[\\w+-]+(\\.[\\w+-]+)*@[\\w]+(\\.[\\w]+)?(?=(\\.[A-Za-z_]{2,3}$|\\.[a-zA-Z]{2,3}$)).*$";
	
	/*** Autowired annotation is used for automatic dependency injection. ***/
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenUtil tokenUtil;

	/*** Simple hello message to check. ***/
	@Override
	public String helloMessage(String token) {
		Long tokenId = tokenUtil.decodeToken(token);
		Optional<User> userByToken = userRepository.findById(tokenId);
		if (!userByToken.get().isVerify()) {
			throw new UserException(NON_VERIFIED_USER);
		} else {
			return "Hello Nikhil...!";
		}
	}

	/*** Adding user ***/
	@Override
	public User addUser(UserDTO user) {
		Long count = userRepository.count();
		if(count > 0) {
			List<User> users = userRepository.findUserByEmail(user.email);
			if(users.size() > 0) {
				throw new UserException("Email is alread existed. Please login...!");
			}
		} 
		return userRepository.save(new User(user));
	}

	/*** Updating user ***/
	@Override
	public User updateUserDetails(UserDTO user, String id, String token) {
		Long tokenId = tokenUtil.decodeToken(token);
		Optional<User> userByTokenId = userRepository.findById(tokenId);

		if (!userByTokenId.get().isVerify()) {
			throw new UserException(NON_VERIFIED_USER);
		} else {
			Optional<User> findUserById = userRepository.findById(Long.parseLong(id));
			if (!findUserById.isPresent()) {
				log.error(ID_NOT_FOUND);
				throw new UserException(ID_NOT_FOUND);
			} else {
				User userData = new User(Long.parseLong(id), user);
				if (findUserById.get().isVerify()) {
					userData.setVerify(true);
					;
				}
				return userRepository.save(userData);
			}
		}
	}

	/*** Read all Users Details. ***/
	@Override
	public List<User> readAllUsersData(String token) {
		Long id = tokenUtil.decodeToken(token);
		Optional<User> findUserByTokenId = userRepository.findById(id);
		if (!findUserByTokenId.get().isVerify()) {
			throw new UserException(NON_VERIFIED_USER);
		} else {
			if (!findUserByTokenId.isPresent()) {
				throw new UserException(TOKEN_INVALID);
			} else {
				return userRepository.findAll();
			}
		}
	}

	/*** read user details by using ID. ***/
	@Override
	public User readUserById(String id, String token) {
		Long tokenId = tokenUtil.decodeToken(token); // decoding token and getting id.
		Optional<User> userByTokenId = userRepository.findById(tokenId);
		if (!userByTokenId.get().isVerify()) {
			throw new UserException(NON_VERIFIED_USER);
		} else {
			if (userByTokenId.isPresent()) {
				return userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new UserException(ID_NOT_FOUND));
			} else {
				return null;
			}
		}
	}

	/*** Delete user details by using ID. ***/
	@Override
	public String deleteUserById(String id, String token) {
		Long tokenId = tokenUtil.decodeToken(token);
		Optional<User> userByTokenId = userRepository.findById(tokenId);
		if (!userByTokenId.get().isVerify()) {
			throw new UserException(NON_VERIFIED_USER);
		} else {
			Optional<User> findEmployeeById = userRepository.findById(Long.parseLong(id));
			if (findEmployeeById.isPresent()) {
				userRepository.deleteById(Long.parseLong(id));
				return "Deleted user details successfully";
			} else {
				return USER_NOT_FOUND;
			}
		}
	}

	/*** Verifying user. ***/
	@Override
	public User userVerification(String token) {
		Long tokenId = tokenUtil.decodeToken(token);
		Optional<User> findUserByTokenId = userRepository.findById(tokenId);
		if (!findUserByTokenId.isPresent()) {
			throw new UserException(ID_NOT_FOUND);
		} else {
			findUserByTokenId.get().setVerify(true);
			return userRepository.save(findUserByTokenId.get());
		}
	}

	/*** User login ***/
	@Override
	public User userLogin(String email, String password) {
		List<User> users = userRepository.findUserByEmail(email);
		boolean pattern_password_matching = Pattern.compile(PASSWORD_REGEX)
				.matcher(password).matches();
		boolean pattern_email_matching = Pattern.compile(EMAIL_REGEX)
				.matcher(email).matches();

		if (!pattern_password_matching && !pattern_email_matching) {
			throw new UserException("Email & Password validation failed...!");
		} else if (!pattern_email_matching) {
			throw new UserException("Email validation failed...!");
		} else if (!pattern_password_matching) {
			throw new UserException(PASSWORD_NOTVALID);
		} else if (users.size() < 1) {
			throw new UserException(USER_NOT_FOUND);
		} else if (!users.get(0).getPassword().equals(password)) {
			throw new UserException("Password is incorrect..!");
		} else {
			return users.get(0);
		}
	}

	/*** Setting new password. ***/
	@Override
	public User setNewPassword(String token, String newPassword) {
		Long tokenId = tokenUtil.decodeToken(token);
		Optional<User> userByTokenId = userRepository.findById(tokenId);
		if (!userByTokenId.get().isVerify()) {
			throw new UserException(NON_VERIFIED_USER);
		} else {
			if (!userByTokenId.isPresent()) {
				throw new UserException(USER_NOT_FOUND);
			} else {
				User user = userByTokenId.get();
				boolean pattern = Pattern.compile(PASSWORD_REGEX)
						.matcher(newPassword).matches();
				if (!pattern) {
					throw new UserException(PASSWORD_NOTVALID);
				} else {
					user.setPassword(newPassword);
					return userRepository.save(user);
				}
			}
		}
	}

}