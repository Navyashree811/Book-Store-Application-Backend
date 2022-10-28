package com.bridgelabz.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.response.Response;

@Component
public interface IBookService {

	Response addBook(BookDto newBook, String token) throws UserException;

	Response updateBook(BookDto newBook, String token, Long bookId) throws UserException;

	Response deleteBook(String token, Long bookId);

	List<BookModel> getAllBook(String token);

	Response changeBookQuantity(String token, Long bookId, int quantity);

	Response changeBookPrice(String token, Long bookId, Double price);

}
