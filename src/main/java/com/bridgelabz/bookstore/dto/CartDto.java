package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class CartDto {
	private Long userId;
	private Long bookId;

	private int quantity;

}