package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class BookDto {

	@NotBlank(message = "BookName is mandatory")
	private String bookName;

	private String author;

	private String bookDescription;

	private String bookLogoMultiPart;

	private Double price;

	private int quantity;
}
