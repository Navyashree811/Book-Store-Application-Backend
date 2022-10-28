package com.bridgelabz.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.dto.CartDto;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.response.Response;

@Component
public interface ICartService {

	Response addToCart(CartDto cartDto, Long bookId, String token);

	Response removeItem(Long cartId) throws BookException;

	Response updateQuantity(String token, Long cartId, int quantity);

	List<CartModel> getAllCartItemsForUser(String token) throws BookException;

	List<CartModel> getAllCartItems() throws BookException;

	public long getOrderId();

}
