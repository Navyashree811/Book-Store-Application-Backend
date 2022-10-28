package com.bridgelabz.bookstore.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.CartDto;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utility.EmailSenderService;
import com.bridgelabz.bookstore.utility.JwtToken;

@Service
public class CartService implements ICartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	JwtToken jwtop;

	@Autowired
	EmailSenderService emailSender;

	@Override
	public Response addToCart(CartDto cartDto, Long bookId, String token) {
		long id = JwtToken.decodeJWT(token);
		Optional<CartModel> book = cartRepository.findByBookIdAndUserId(bookId, id);
		if (book.isPresent()) {
			cartRepository.delete(book.get());
			BeanUtils.copyProperties(cartDto, book.get());
			book.get().setUserId(id);
			cartRepository.save(book.get());
		} else {
			CartModel cartModel = new CartModel();
			BeanUtils.copyProperties(cartDto, cartModel);
			cartModel.setUserId(id);
			cartModel.setBookId(bookId);
			cartRepository.save(cartModel);
		}
		return new Response("Book Added to Cart Successfully", HttpStatus.OK.value(), id);

	}

	@Override
	public Response removeItem(Long cartId) throws BookException {

		CartModel cartModel = cartRepository.findByBookId(cartId)
				.orElseThrow(() -> new BookException("Book is Not Added To Cart", HttpStatus.NOT_FOUND.value()));
		int quantity = cartModel.getQuantity();

		cartModel.setTotalPrice(cartModel.getTotalPrice() * (quantity - 1) / quantity);
		quantity--;
		cartModel.setQuantity(quantity);
		cartRepository.save(cartModel);
		return new Response("One Quantity Removed Successfully", HttpStatus.OK.value(), cartModel);
	}

	@Override
	public long getOrderId() {
		Date date = new Date();
		long time = date.getTime();
		return time;
	}

	@Override
	public List<CartModel> getAllCartItemsForUser(String token) {
		long id = JwtToken.decodeJWT(token);
		System.out.println("id" + id);
		List<CartModel> items = cartRepository.findAllByUserId(id);
		return items;
	}

	@Override
	public Response updateQuantity(String token, Long cartId, int quantity) {
		JwtToken.decodeJWT(token);
		cartRepository.updateQuantity(quantity, cartId);

		return new Response(HttpStatus.OK.value(), "Cart Quantity Updated Successfully ");
	}

	@Override
	public List<CartModel> getAllCartItems() throws BookException {
		List<CartModel> items = cartRepository.findAll();
		return items;
	}

}
