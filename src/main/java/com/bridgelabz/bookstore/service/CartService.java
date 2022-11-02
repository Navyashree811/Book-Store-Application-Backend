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
import com.bridgelabz.bookstore.exception.CartException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utility.EmailSenderService;
import com.bridgelabz.bookstore.utility.JwtToken;

@Service
public class CartService implements ICartService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	JwtToken jwtop;

	@Autowired
	EmailSenderService emailSender;

	@Override
	public Response addToCart(CartDto cartDto, String token) throws Exception {
		long id = JwtToken.decodeJWT(token);
//		Optional<CartModel> cart = cartRepository.findByBookIdAndUserId(cartDto.getBookId(), id);
		Optional<CartModel> cart = cartRepository.findById(cartDto.getBookId());
		Optional<UserModel> user = userRepository.findById(cartDto.getUserId());
		Optional<BookModel> book = bookRepository.findById(cartDto.getBookId());
		if (user.isPresent()) {
			if (book.isEmpty()) {
				throw new CartException("Book Not Available", HttpStatus.FORBIDDEN.value());
			}

			if (cart.isPresent()) {
				throw new CartException("Book Alreay Added in cart", HttpStatus.FORBIDDEN.value());

			} else {

				CartModel cartModel = new CartModel();

				BeanUtils.copyProperties(cartDto, cartModel);
				cartModel.setUser(user.get());
				cartModel.setBook(book.get());

				BookModel bookModel = bookRepository.findByBookId(cartDto.getBookId());
				cartModel.setTotalPrice(bookModel.getPrice() * cartDto.getQuantity());

				cartRepository.save(cartModel);

			}
			return new Response("Book Added to Cart Successfully", HttpStatus.OK.value(), id);

		} else
			throw new UserException("User does not exists", HttpStatus.FORBIDDEN.value());
	}

	@Override
	public Response removeItem(Long cartId) throws BookException {

		CartModel cartModel = cartRepository.findByBookId(cartId)
				.orElseThrow(() -> new BookException("Book is Not Added To Cart", HttpStatus.NOT_FOUND.value()));

		cartRepository.removeFromCart(cartId);
		return new Response("One Item Removed Successfully", HttpStatus.OK.value(), cartModel);
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
		List<CartModel> items = cartRepository.getAllByUserId(id);
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
