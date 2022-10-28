package com.bridgelabz.bookstore.service;

import java.time.LocalDateTime;

import com.bridgelabz.bookstore.dto.*;
import com.bridgelabz.bookstore.model.*;
import com.bridgelabz.bookstore.repository.*;
import com.bridgelabz.bookstore.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.exception.UserVerificationException;
import com.bridgelabz.bookstore.utility.EmailSenderService;
import com.bridgelabz.bookstore.utility.JwtToken;

@Service
public class UserService implements IUserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	JwtToken jwtop;

	@Autowired
	EmailSenderService emailSender;

	private static final String VERIFICATION_URL = "http://localhost:8080/user/verify/";
	private static final String RESETPASSWORD_URL = "http://localhost:8080/user/resetpassword/";

	@Override
	public boolean register(RegistrationDto registrationDto) throws UserException {
		UserModel user = userRepository.findByEmailId(registrationDto.getEmailId());
		if (user != null) {
			throw new UserException("Registration Failed!", HttpStatus.FORBIDDEN.value());
		}
		UserModel userDetails = new UserModel();
		BeanUtils.copyProperties(registrationDto, userDetails);
		userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		userDetails.setDob(LocalDateTime.now());
		userDetails.setRegisteredAt(LocalDateTime.now());
		userDetails.setUpdatedAt(LocalDateTime.now());
		userDetails.setPurchaseDate(LocalDateTime.now());
		userDetails.setExpiryDate(LocalDateTime.now());
		userRepository.save(userDetails);
		UserModel sendMail = userRepository.findByEmailId(registrationDto.getEmailId());
		String response = VERIFICATION_URL + JwtToken.createJWT(sendMail.getUserId());
		System.out.println(response);

		emailSender.sendEmail(registrationDto.getEmailId(), "Registration Link...",
				"Link For Verification: " + response);
		return true;

	}

	@Override
	public boolean verify(String token) {
		long id = JwtToken.decodeJWT(token);
		UserModel userInfo = userRepository.findByUserId(id);
		if (id > 0 && userInfo != null) {
			if (!userInfo.isVerify()) {
				userInfo.setVerify(true);
				userInfo.setUpdatedAt(LocalDateTime.now());
				userRepository.save(userInfo);
				return true;
			}
			throw new UserVerificationException(HttpStatus.CREATED.value(), "User already verified!");
		}
		return false;
	}

	@Override
	public UserDetailsResponse forgetPassword(ForgotPasswordDto userMail) {
		UserModel isIdAvailable = userRepository.findByEmailId(userMail.getEmailId());
		if (isIdAvailable != null && isIdAvailable.isVerify()) {
			String token = JwtToken.createJWT(isIdAvailable.getUserId());
			String response = RESETPASSWORD_URL + token;
			emailSender.sendEmail(userMail.getEmailId(), "ResetPassword Link...", response);
			return new UserDetailsResponse(HttpStatus.OK.value(), "ResetPassword link Successfully", token);
		}
		return new UserDetailsResponse(HttpStatus.OK.value(), "Eamil ending failed");
	}

	@Override
	public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserNotFoundException {
		if (resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())) {
			long id = JwtToken.decodeJWT(token);
			UserModel isIdAvailable = userRepository.findByUserId(id);
			if (isIdAvailable != null) {
				isIdAvailable.setPassword(bCryptPasswordEncoder.encode((resetPassword.getNewPassword())));
				userRepository.save(isIdAvailable);
				return true;
			}
			throw new UserNotFoundException("No User Exist");
		}
		return false;
	}

	@Override
	public LoginResponse login(LoginDto loginDTO) throws UserException {
		UserModel userCheck = userRepository.findByEmailId(loginDTO.getEmailId());

		if (userCheck == null) {
			throw new UserException("Please Register First!", HttpStatus.NOT_FOUND.value());
		}
		if (!userCheck.isVerify()) {
			throw new UserException("Please Verified Your EmailId!", HttpStatus.BAD_REQUEST.value());
		}

		if (bCryptPasswordEncoder.matches(loginDTO.getPassword(), userCheck.getPassword())) {
			String token = JwtToken.createJWT(userCheck.getUserId());
			userRepository.save(userCheck);
			return new LoginResponse("Hi " + userCheck.getFirstName(), token, HttpStatus.OK.value(),
					"Login Successfully");
		}

		throw new UserException("Invalid Credential, Try Again!", HttpStatus.FORBIDDEN.value());
	}

}