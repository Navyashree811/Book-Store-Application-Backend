package com.bridgelabz.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.response.Response;

@Component
public interface IOrderService {

	Response orderPlaced(String token, String address) throws BookException;

	public long getOrderId();

	public Response cancelOrder(String token, Long orderId) throws BookException;

	List<OrderModel> getAllOrders();

	List<OrderModel> getAllOrdersByUser(String token);

}