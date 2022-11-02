package com.bridgelabz.bookstore.controller;

import java.util.List;

import com.bridgelabz.bookstore.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.ICartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ICartService cartService;

//	"Add Books to Cart"
	@PostMapping("/AddToCart")
	public ResponseEntity<Response> AddToCart(@RequestBody CartDto cartDto, @RequestHeader("token") String token)
			throws BookException, Exception {
		Response response = cartService.addToCart(cartDto, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//	"Remove Items from Cart"
	@DeleteMapping("/removeFromCart")
	public ResponseEntity<Response> removeFromCart(@RequestParam Long cartId) throws BookException {
		Response response = cartService.removeItem(cartId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//	"Get All Items from Cart User"
	@GetMapping("/getAllItemsFromCart")
	public List<CartModel> getAllItemsFromCart(@RequestHeader("token") String token) throws BookException {
		return cartService.getAllCartItemsForUser(token);
	}

	@PutMapping(value = "/updateQuantity")
	public ResponseEntity<Response> updateQuantity(@RequestHeader("token") String token, @RequestParam Long cartId,
			@RequestParam int quantity) throws UserException {
		Response cart = cartService.updateQuantity(token, cartId, quantity);
		return new ResponseEntity<Response>(cart, HttpStatus.OK);
	}

	// "Get All Items from Cart"
	@GetMapping("/getAllFromCart")
	public List<CartModel> getAllCartItems() throws BookException {
		return cartService.getAllCartItems();
	}
}
