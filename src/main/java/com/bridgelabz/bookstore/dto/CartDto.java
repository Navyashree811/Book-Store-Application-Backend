package com.bridgelabz.bookstore.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CartDto {
	private Long userId;

	private int quantity;
	private double totalPrice;

}
