package com.bridgelabz.bookstore.exception;

import lombok.Data;

@Data
public class CartException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	int statusCode;
	Object data;

	public CartException(String message, int status, Object data) {
		this.message = message;
		this.statusCode = status;
		this.data = data;
	}

	public CartException(String message, int status) {
		this.message = message;
		this.statusCode = status;
	}
}