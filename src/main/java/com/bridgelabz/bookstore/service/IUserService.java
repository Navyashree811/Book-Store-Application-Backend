package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.*;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.response.LoginResponse;
import com.bridgelabz.bookstore.response.UserDetailsResponse;

@Component
public interface IUserService {

	boolean register(RegistrationDto registrationDto) throws UserException;

	boolean verify(String token);

	UserDetailsResponse forgetPassword(ForgotPasswordDto emailId);

	boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserNotFoundException;

	LoginResponse login(LoginDto logindto) throws UserException;

	boolean deleteUser(String token) throws UserNotFoundException;

}
