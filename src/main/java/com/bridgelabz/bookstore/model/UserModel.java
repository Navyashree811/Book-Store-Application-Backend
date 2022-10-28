package com.bridgelabz.bookstore.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;

	private String firstName;
	private String lastName;

	private String kyc;

	@CreationTimestamp
	public LocalDateTime dob;

	@CreationTimestamp
	public LocalDateTime registeredAt;

	@UpdateTimestamp
	public LocalDateTime updatedAt;

	@NotNull
	private String password;

	@NotNull
	@Column(unique = true)
	private String emailId;

	@Column(columnDefinition = "boolean default false")
	private boolean verify;

	private long otp;

	@CreationTimestamp
	public LocalDateTime purchaseDate;

	@CreationTimestamp
	public LocalDateTime expiryDate;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "userbooks", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "book_id") })
	private List<BookModel> book;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "book_Id")
	private List<BookModel> books;

}
