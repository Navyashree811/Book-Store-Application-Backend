package com.bridgelabz.bookstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	private int status;
	private String message;
	private Object data;
	private int size;
	private LoginResponse loginResponse;
	private long orderId;

	public Response(int status, String message, long orderId) {
		this.status = status;
		this.message = message;
		this.orderId = orderId;
	}

	public Response(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(String message, int status, Object data) {
		this.message = message;
		this.status = status;
		this.data = data;
	}

	public Response(String message, int status, LoginResponse loginResponse) {
		this.message = message;
		this.status = status;
		this.loginResponse = loginResponse;
	}

	public Response(int size, String message, int status, Object data) {
		this.size = size;
		this.message = message;
		this.status = status;
		this.data = data;
	}

}
