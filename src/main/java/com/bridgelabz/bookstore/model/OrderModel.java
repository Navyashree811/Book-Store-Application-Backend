//package com.bridgelabz.bookstore.model;
//
//import lombok.Data;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@Table(name = "Order")
//public class OrderModel {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	private Long id;
//	private LocalDateTime orderDate;
//	private double price;
//	private int quantity;
//	private String Address;
//	private long userId;
//	private long bookId;
//	private boolean cancel;
//}