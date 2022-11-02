package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	/* OrderIDGeneratorMethod */
	@GetMapping("/orderId")
	public long getOrderId() {
		return orderService.getOrderId();
	}

	@PostMapping("/orderPlaced")
	public ResponseEntity<Response> orderPlaced(@RequestHeader("token") String token, @RequestParam String address)
			throws UserException, BookException {
		Response book = orderService.orderPlaced(token, address);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
	}

	@PutMapping("/cancelOrder")
	public ResponseEntity<Response> cancelOrder(@RequestHeader("token") String token, @RequestParam Long orderId)
			throws UserException, BookException {
		Response book = orderService.cancelOrder(token, orderId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
	}

	@GetMapping("/getAllOrders")
	public List<OrderModel> getAllOrders() throws UserException, BookException {
		List<OrderModel> book = orderService.getAllOrders();
		return book;
	}

	@GetMapping("/getAllOrdersForUser")
	public List<OrderModel> getAllOrdersForUser(@RequestHeader("token") String token)
			throws UserException, BookException {
		List<OrderModel> book = orderService.getAllOrdersByUser(token);
		return book;
	}

}