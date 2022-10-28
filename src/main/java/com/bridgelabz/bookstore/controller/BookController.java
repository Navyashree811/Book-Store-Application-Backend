package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.model.BookModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.IBookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	IBookService bookService;

	@PostMapping(value = "/addBook")
	public ResponseEntity<Response> addBook(@RequestBody BookDto newBook, @RequestHeader("token") String token)
			throws UserException {
		Response addedBook = bookService.addBook(newBook, token);
		return new ResponseEntity<Response>(addedBook, HttpStatus.OK);
	}

	@PutMapping(value = "/updateBook/{bookId}")
	public ResponseEntity<Response> updateBook(@RequestBody BookDto newBook, @RequestHeader("token") String token,
			@PathVariable("bookId") Long bookId) throws UserException {
		bookService.updateBook(newBook, token, bookId);
		return new ResponseEntity<Response>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteBook/{bookId}")
	public ResponseEntity<Response> deleteBook(@RequestHeader("token") String token,
			@PathVariable("bookId") Long bookId) throws UserException {
		bookService.deleteBook(token, bookId);
		return new ResponseEntity<Response>(HttpStatus.OK);
	}

	@GetMapping("/getAllBooks")
	public ResponseEntity<Response> getAllBooks(@RequestHeader("token") String token) {
		List<BookModel> book = bookService.getAllBook(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Get all Sellers", 200, book));
	}

	@PutMapping(value = "/changeBookQuantity")
	public ResponseEntity<Response> changeBookQuantity(@RequestHeader("token") String token, @RequestParam Long bookId,
			@RequestParam int quantity) throws UserException {
		Response addedBook = bookService.changeBookQuantity(token, bookId, quantity);
		return new ResponseEntity<Response>(addedBook, HttpStatus.OK);
	}

	@PutMapping(value = "/changeBookPrice")
	public ResponseEntity<Response> changeBookPrice(@RequestHeader("token") String token, @RequestParam Long bookId,
			@RequestParam Double price) throws UserException {
		Response addedBook = bookService.changeBookPrice(token, bookId, price);
		return new ResponseEntity<Response>(addedBook, HttpStatus.OK);
	}

}
