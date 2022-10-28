package com.bridgelabz.bookstore.response;

import lombok.Data;

@Data
public class LoginResponse {

	private String name;
	private String token;
	private int status;
	private String message;

	public LoginResponse(String name, String token, int status, String message) {

		this.name = name;
		this.token = token;
		this.status = status;
		this.message = message;
	}

}
