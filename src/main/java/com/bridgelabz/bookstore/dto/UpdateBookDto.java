package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDto {
	@NotBlank(message = "BookName is mandatory")
	private String bookName;

	private String author;

	private String bookDescription;

	private String bookLogoMultiPart;

	private Double price;

	private int quantity;
}
