package com.bridgelabz.bookstore.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.repository.OrderRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utility.EmailSenderService;
import com.bridgelabz.bookstore.utility.JwtToken;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	JwtToken jwtop;

	@Autowired
	ICartService cartService;

	@Autowired
	EmailSenderService emailSender;

	@Override
	public Response orderPlaced(String token, String address) throws BookException {
		long id = JwtToken.decodeJWT(token);
		UserModel userInfo = userRepository.findByUserId(id);
		Optional<CartModel> cart = cartRepository.findById(id);
		List<CartModel> allItemFromCart = cartService.getAllCartItemsForUser(token);
		long orderId = getOrderId();
		String bookName = "\n";
		String price = "";
		double totalPrice = 0;
		for (CartModel cartModel : allItemFromCart) {
			BookModel bookModel = bookRepository.findByBookId(cart.get().getBook().getBookId());
			bookName = bookName + bookModel.getBookName() + " (Rs." + price + bookModel.getPrice() + ")" + " X "
					+ "(Quantity " + cartModel.getQuantity() + ")\n";
			totalPrice = totalPrice + cartModel.getTotalPrice();
			bookModel.setQuantity(bookModel.getQuantity() - cartModel.getQuantity());
			bookRepository.save(bookModel);

			OrderModel order = new OrderModel();
			BeanUtils.copyProperties(cartModel, order);
			order.setOrderId(orderId);
			order.setPrice(cartModel.getTotalPrice());
			order.setQuantity(cartModel.getQuantity());
			order.setCancel(false);
			order.setAddress(address);
			order.setOrderDate(LocalDateTime.now());
			orderRepository.save(order);
			System.out.println("cart deletion started");
		}
		cartRepository.deleteAllByUser(id);
		if (userInfo != null) {
			String response = "==================\n" + "BOOK STORE \n" + "==================\n\n" + "Hello "
					+ userInfo.getFirstName() + ",\n\n" + "Your order has been placed successfully.\n"
					+ "----------------------------------------------------------------\n" + "Your OrderId is "
					+ orderId + "\n" + "Book Name : " + bookName + "\n" + "Total Items : " + allItemFromCart.size()
					+ "\n" + "----------------------------------------------------------------\n" + "Total Price : Rs."
					+ totalPrice + "\n" + "\n\n" + "Thank you for Shopping with us.\n"
					+ "Have a great Experience with us !!" + "\n\n" + "Thank you,\n"
					+ "Online Book Store Team, Mumbai\n" + "Contact us\n" + "mob. : +91-1234567890\n"
					+ "email : bridgelabz@bookstore.com\n";

			emailSender.sendEmail(userInfo.getEmailId(), "Order Placed Successfully...", response);

			return new Response(HttpStatus.OK.value(), "Order Successfull", orderId);
		}
		throw new BookException(("Book Not Verified"), HttpStatus.OK.value());
	}

	@Override
	public long getOrderId() {
		Date date = new Date();
		long time = date.getTime();
		return time;
	}

	@Override
	public Response cancelOrder(String token, Long orderId) throws BookException {

		long id = JwtToken.decodeJWT(token);
		UserModel userInfo = userRepository.findByUserId(id);

		orderRepository.cancelOrder(orderId);
		if (userInfo != null) {
			String response = "==================\n" + "BOOK STORE \n" + "==================\n\n" + "Hello "
					+ userInfo.getFirstName() + ",\n\n" + "Your order has been cancelled successfully.\n"
					+ "----------------------------------------------------------------\n" + "Your OrderId is "
					+ orderId + "\n" + "\n" + "\n\n" + "Thank you for Shopping with us.\n"
					+ "Have a great Experience with us !!" + "\n\n" + "Thank you,\n"
					+ "Online Book Store Team, Mumbai\n" + "Contact us\n" + "mob. : +91-1234567890\n"
					+ "email : bridgelabz@bookstore.com\n";

			emailSender.sendEmail(userInfo.getEmailId(), "Order Cancel Successfully...", response);
			return new Response(HttpStatus.OK.value(), "Order Cancel Successfully...", orderId);

		}
		throw new BookException(("Book Not Verified"), HttpStatus.OK.value());
	}

	@Override
	public List<OrderModel> getAllOrders() {

		return orderRepository.getAllOrders();
	}

	@Override
	public List<OrderModel> getAllOrdersByUser(String token) {
		long id = JwtToken.decodeJWT(token);
		List<OrderModel> order = orderRepository.findAllByUser(id);
		return order;
	}

}