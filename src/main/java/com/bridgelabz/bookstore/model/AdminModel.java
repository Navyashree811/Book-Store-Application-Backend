package com.bridgelabz.bookstore.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Table(name = "Admin")
@Data
public class AdminModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminId;

	@NotBlank(message = "AdminName is mandatory")
	private String firstName;

	private String lastName;

	@Email
	private String emailId;

	@NotNull
	private String password;

}