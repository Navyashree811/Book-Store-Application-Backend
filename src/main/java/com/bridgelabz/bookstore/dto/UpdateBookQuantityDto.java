package com.bridgelabz.bookstore.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UpdateBookQuantityDto {

	private int quantity;

}
