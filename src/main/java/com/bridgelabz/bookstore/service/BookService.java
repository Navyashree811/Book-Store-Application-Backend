package com.bridgelabz.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utility.JwtToken;

import org.springframework.http.HttpStatus;

@Service
public class BookService implements IBookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	JwtToken jwtop;

	@Override

	public Response addBook(BookDto newBook, String token) throws UserException {

		BookModel book = new BookModel();
		BeanUtils.copyProperties(newBook, book);
		bookRepository.save(book);
		return new Response("Book is added successfully but need to verified", HttpStatus.OK.value(), book);

	}

	@Override
	public Response updateBook(BookDto newBook, String token, Long bookId) throws UserException {
		JwtToken.decodeJWT(token);
		Optional<BookModel> book = bookRepository.findById(bookId);

		BeanUtils.copyProperties(newBook, book.get());
		bookRepository.save(book.get());
		return new Response(HttpStatus.OK.value(), "Book update Successfully Need to Verify");

	}

	@Override
	public Response deleteBook(String token, Long bookId) {
		JwtToken.decodeJWT(token);
		bookRepository.deleteById(bookId);
		// elasticSearchService.deleteNote(bookId);
		return new Response(HttpStatus.OK.value(), "Book deleted Successfully ");

	}

	@Override
	public List<BookModel> getAllBook(String token) {

		JwtToken.decodeJWT(token);
		return bookRepository.findAll();

	}

	@Override
	public Response changeBookQuantity(String token, Long bookId, int quantity) {

		JwtToken.decodeJWT(token);
		bookRepository.changeBookQuantity(bookId, quantity);

		return new Response(HttpStatus.OK.value(), "Book Quantity Updated Successfully ");
	}

	@Override
	public Response changeBookPrice(String token, Long bookId, Double price) {
		JwtToken.decodeJWT(token);
		bookRepository.changeBookPrice(bookId, price);

		return new Response(HttpStatus.OK.value(), "Book Price Updated Successfully ");
	}

}
