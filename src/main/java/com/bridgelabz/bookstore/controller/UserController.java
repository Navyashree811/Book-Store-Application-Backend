package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.response.LoginResponse;
import com.bridgelabz.bookstore.response.RegistrationResponse;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.response.UserDetailsResponse;
import com.bridgelabz.bookstore.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationDto registrationDto)
			throws UserException {

		boolean u = userService.register(registrationDto);
		RegistrationResponse responseDto = new RegistrationResponse("Registration Successful !", u);

		return new ResponseEntity<RegistrationResponse>(responseDto, HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDTO) throws UserException {
		LoginResponse response = userService.login(loginDTO);
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/verify/{token}")
	public ResponseEntity<UserDetailsResponse> userVerification(@PathVariable("token") String token) {

		if (userService.verify(token))
			return ResponseEntity.status(HttpStatus.OK)
					.body(new UserDetailsResponse(HttpStatus.OK.value(), "Verified Successfully"));

		return ResponseEntity.status(HttpStatus.OK)
				.body(new UserDetailsResponse(HttpStatus.BAD_REQUEST.value(), "Not Verified"));
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<UserDetailsResponse> forgotPassword(@RequestBody ForgotPasswordDto emailId) {

		UserDetailsResponse response = userService.forgetPassword(emailId);
		return new ResponseEntity<UserDetailsResponse>(response, HttpStatus.OK);
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordDto resetPassword,
			@RequestParam("token") String token) throws UserNotFoundException {

		if (userService.resetPassword(resetPassword, token))
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(HttpStatus.OK.value(), "Password is Update Successfully"));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(HttpStatus.BAD_REQUEST.value(),
				"Password and Confirm Password doesn't matched please enter again"));
	}

	@DeleteMapping("/deleteUser")
	public ResponseEntity<Response> deleteUser(@RequestHeader("token") String token) throws UserNotFoundException {

		userService.deleteUser(token);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(HttpStatus.OK.value(), "User Delete Successfully"));

	}

}